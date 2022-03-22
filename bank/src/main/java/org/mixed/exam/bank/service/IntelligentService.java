package org.mixed.exam.bank.service;

import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.strategy.EvaluateStrategy;
import org.mixed.exam.bank.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IntelligentService
{
    private IntelligentParam param;
    @Autowired
    private QuerySubjectService querySubjectService;

    //初始化种群
    private void initIndividual(List<Individual> individuals,IntelligentParam param,int size)
    {
        for(int i=0;i<size;i++)
        {
            individuals.add(getRandomIndividual(param));
        }
    }

    //随机生成个体
    private Individual getRandomIndividual(IntelligentParam param)
    {
        List<IntelligentParam.SubjectDistribution> distributions = param.getDistributions();
        Individual individual = new Individual(subjects.size());
        for(int i=0;i<distributions.size();i++)
        {
            String type=distributions.get(i).getType();
            int count = distributions.get(i).getCount();
            Set<Integer> repeat = new HashSet<>();
            while (count-- >0)
            {
                int length=subjectsMap.get(type).size();
                int genePosAtMap= MathUtil.getRandomInt(0,length-1);
                while (repeat.contains(genePosAtMap))
                {
                    genePosAtMap=MathUtil.getRandomInt(0,length-1);
                }
                repeat.add(genePosAtMap);
                Question q_to_add=subjectsMap.get(type).get(genePosAtMap);
                individual.setGene(subjects.indexOf(q_to_add),1);
            }
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
        int variationCount=(int)(individuals.size()*variationRate);
        while (variationCount-- > 0)
        {
            Individual individual = individuals.get(MathUtil.getRandomInt(0, individuals.size() - 1));
            int count = (int)(individual.length*strength);
            while (count-- > 0)
            {
                individual.setGene(MathUtil.getRandomInt(0,individual.length-1),
                        MathUtil.getRandomInt(0,1));
            }
        }
    }

    private void printIndividuals(List<Individual> individuals,int generation)
    {
        Collections.sort(individuals);
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("generation:"+generation);
        System.out.println("total:"+individuals.size());
        System.out.println(individuals);
        System.out.println("average:"+calAverageFitness(individuals));
    }

    private static int INIT_INDIVIDUAL_SIZE=50;
    //构建试卷
    public Paper build(IntelligentParam param) {
        this.param=param;
        initSubjectInfo(param.getCourseID());
        List<Individual> individuals = new ArrayList<>();
        initIndividual(individuals,param, INIT_INDIVIDUAL_SIZE);
        for(int generation=1;generation<=50;generation++)
        {
            evaluate(individuals,param);
            printIndividuals(individuals,generation);
            eliminate(individuals);
            cross(individuals,50-individuals.size());
            variation(individuals,0.1,0.05);//变异
            try {
                Thread.sleep(1000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Question> subjects;
    private Map<String,List<Question>> subjectsMap=new HashMap<>();
    private void initSubjectInfo(String courseID)
    {
        subjects = querySubjectService.getSubjects(
                null,null,null,null,String.valueOf(courseID),null,null);
        for(Question q : subjects)
        {
            String type = q.getType();
            if(!subjectsMap.containsKey(type))
            {
                subjectsMap.put(type,new ArrayList<>());
            }
            subjectsMap.get(type).add(q);
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
        public int fitness;
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
            return fitness-i.fitness;
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

