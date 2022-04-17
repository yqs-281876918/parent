package org.mixed.exam.bank.service;

import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.strategy.EvaluateStrategy;
import org.mixed.exam.bank.util.MathUtil;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IntelligentService
{
    @Autowired
    private QuerySubjectService querySubjectService;

    //初始化种群
    private void initIndividual(List<Individual> individuals,int size)
    {
        for(int i=0;i<size;i++)
        {
            individuals.add(getRandomIndividual());
        }
    }

    //随机生成个体
    private Individual getRandomIndividual()
    {
        int geneCount=MathUtil.getRandomInt(1,subjects.size());
        Individual individual = new Individual(subjects.size());
        List<Integer> nums = new ArrayList<>();
        for (int i=0;i<subjects.size();i++){
            nums.add(i);
        }
        while (geneCount-- > 0){
            int num_pos = MathUtil.getRandomInt(0,nums.size()-1);
            int gene_pos = nums.remove(num_pos);
            individual.setGene(gene_pos,1);
        }
        return individual;
    }

    //评估适应度
    private void evaluate(List<Individual> individuals,IntelligentParam param)
    {
        for (Individual individual : individuals)
        {
            individual.unsetFitness();
            for(EvaluateStrategy strategy : EvaluateStrategy.strategies)
            {
                strategy.evaluate(individual,param,subjects);
            }
        }
    }

    //计算平均适应度
    private double calAverageFitness(List<Individual> individuals)
    {
        double sum = 0;
        for(Individual individual : individuals)
        {
            sum+=individual.fitness;
        }
        return sum/individuals.size();
    }

    //淘汰
    private void eliminate(List<Individual> individuals)
    {
        Collections.sort(individuals);
        int avg=(int)calAverageFitness(individuals);
        int eliminateCount=0;
        for(Individual individual : individuals)
        {
            if(individual.fitness<avg)
            {
                eliminateCount++;
            }
            else
            {
                break;
            }
        }
        while (eliminateCount-- > 0)
        {
            individuals.remove(0);
        }
    }

    //交叉
    private void cross(List<Individual> individuals,int bornCount)
    {
        while (bornCount-- >0)
        {
            Individual father=individuals.get(MathUtil.getRandomInt(0,individuals.size()-1));
            Individual mother=individuals.get(MathUtil.getRandomInt(0,individuals.size()-1));
            while (father==mother)
            {
                mother=individuals.get(MathUtil.getRandomInt(0,individuals.size()-1));
            }
            Individual son = new Individual(Math.min(mother.length,father.length));
            for(int i=0;i<son.length;i++)
            {
                int choice=MathUtil.getRandomInt(0,1);
                if(choice==0)
                {
                    son.setGene(i,mother.getGene(i));
                }
                else if(choice==1)
                {
                    son.setGene(i,father.getGene(i));
                }
            }
            individuals.add(son);
        }
    }

    //变异
    private void variation(List<Individual> individuals,double variationRate,double strength)
    {
        sortIndividuals(individuals);
        int variationCount=(int)(individuals.size()*variationRate);
        if(variationCount==0){
            variationCount=1;
        }
        for(int i=0;i<=variationCount;i++)
        {
            Individual individual = individuals.get(i);
            int count = (int)(individual.length*strength);
            if(count==0){
                count=1;
            }
            while (count-- > 0)
            {
                individual.setGene(MathUtil.getRandomInt(0,individual.length-1),
                        MathUtil.getRandomInt(0,1));
            }
        }
    }

    private void sortIndividuals(List<Individual> individuals){
        Collections.sort(individuals);
    }
    private void printIndividuals(List<Individual> individuals,int generation)
    {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("generation:"+generation);
        System.out.println("total:"+individuals.size());
        System.out.println(individuals);
        System.out.println("average:"+calAverageFitness(individuals));
    }

    private static int INIT_INDIVIDUAL_SIZE=100;
    private static int GENERATION_COUNT=200;
    //构建试卷
    public Paper build(IntelligentParam param) {
        initSubjectInfo(param);
        List<Individual> individuals = new ArrayList<>();
        initIndividual(individuals, INIT_INDIVIDUAL_SIZE);
        int generation=1;
        while (true)
        {
            evaluate(individuals,param);
            sortIndividuals(individuals);
            printIndividuals(individuals,generation++);
            if(generation<=400&&individuals.get(individuals.size()-1).fitness>=399.4){
                break;
            }
            if(generation>400){
                break;
            }
            eliminate(individuals);
            variation(individuals,0.05,0.2);//变异
            cross(individuals,INIT_INDIVIDUAL_SIZE-individuals.size());
        }
        return null;
    }

    private List<Question> subjects;
    private void initSubjectInfo(IntelligentParam param)
    {
        subjects = new ArrayList<>();
        List<String> types= SubjectUtil.getTypeList();
        for(String type:types){
            List<Question> tempList=querySubjectService.getSubjects(type,null,null,null,
                    String.valueOf(param.getCourseID()),null,null);
            subjects.addAll(tempList);
        }
    }
    public static class Individual implements Comparable<Individual>
    {
        public int length;
        private int geneCount=0;
        public int getGeneCount()
        {
            return geneCount;
        }
        private byte[] genes;
        public int getGene(int index)
        {
            int pos=index/8;
            int offset=index-pos*8;
            if((genes[pos]&(1<<(7-offset)))>0)
            {
                return 1;
            }
            return 0;
        }
        public void setGene(int index,int gene)
        {
            int pos=index/8;
            int offset=index-pos*8;
            if(gene==1)
            {
                if(getGene(index)==1)
                {
                    return;
                }
                geneCount++;
                genes[pos]=(byte)(genes[pos]|(1<<(7-offset)));
            }
            if(gene==0)
            {
                if(getGene(index)==0)
                {
                    return;
                }
                geneCount--;
                byte temp=(byte) 255;
                temp-=1<<(7-offset);
                genes[pos]=(byte)(genes[pos]&temp);
            }
        }
        public String printGene()
        {
            StringBuilder sb=new StringBuilder();
            sb.append('[');
            for(int i=0;i<length;i++)
            {
                if(i%8==0&&i!=0)
                {
                    sb.append(' ');
                }
                sb.append(getGene(i));
            }
            sb.append(']');
            return sb.toString();
        }
        public double fitness;
        public void unsetFitness()
        {
            fitness=0;
        }
        public Individual(int length)
        {
            this.length=length;
            genes=new byte[(int)Math.ceil(length/8.0)];
        }

        @Override
        public int compareTo(Individual i) {
            if(fitness<i.fitness){
                return -1;
            }
            if(fitness==i.fitness){
                return 0;
            }
            return 1;
        }

        @Override
        public String toString() {
            return "Individual{" +
                    "fitness=" + fitness +
                    " genes="+printGene()+
                    '}';
        }

    }
}

