package org.mixed.exam.paper.service;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.bank.api.util.SubjectUtil;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;
import org.mixed.exam.paper.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IntelligentService
{
    private IntelligentParam param;
    @Autowired
    private SubjectClient subjectClient;
    //随机数生成器
    private static Random random = new Random(System.currentTimeMillis());
    private int getRandomInt(int lower,int upper)
    {
        return random.nextInt(upper-lower+1)+lower;
    }

    private int daysBetween(Date now, Date past) {
        int difference = (int)((now.getTime()-past.getTime())/86400000);
        return Math.abs(difference);
    }

    //初始化种群
    private void initIndividual(List<Individual> individuals,List<Question> subjects,IntelligentParam param,int size)
    {
//        for(int i=0;i<size;i++)
//        {
//            individuals.add(getRandomIndividual(geneLength));
//        }
    }

    //随机生成个体
    private Individual getRandomIndividual(int geneLength)
    {
        Individual individual = new Individual(geneLength);
        int geneCount=getRandomInt(0,geneLength);
        while (geneCount > 0)
        {
            int genePos=getRandomInt(0,individual.length-1);
            //保证不重复
            while (individual.getGene(genePos)==1)
            {
                genePos=(genePos+1)%individual.length;
            }
            individual.setGene(genePos,1);
            geneCount--;
        }
        return individual;
    }

    //评估适应度
    private void evaluate(List<Individual> individuals)
    {
//        for (Individual individual : individuals)
//        {
//            individual.fitness=1;
//            //评估题目数量
//            int subjectCount=0;
//            for(int i=0;i<individual.length;i++)
//            {
//                if(individual.getGene(i)==1)
//                {
//                    subjectCount++;
//                }
//            }
//            if(subjectCount==param.getSubjectCount())
//            {
//                individual.fitness*=100;
//            }
//            else
//            {
//                int deviation= (int) Math.ceil(((double)Math.abs(subjectCount-param.getSubjectCount())/param.getSubjectCount()*100.0));
//                individual.fitness*=normpdfInt(deviation);
//            }
//        }
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

    private static double SGM=0.6746;
    private static double RATIO=169.0971;
    private double normpdfDouble(double x)
    {
        double ans=(1.0/(Math.sqrt(2*Math.PI)*SGM))*Math.exp((-x*x)/(2*SGM*SGM));
        ans*=RATIO;
        return ans;
    }
    private Map<Integer,Double> normpdfCache=new HashMap<>();
    private double normpdfInt(int x)//用x%表示百分比,利于使用缓存提高性能
    {
        if(normpdfCache.containsKey(x))
        {
            return normpdfCache.get(x);
        }
        normpdfCache.put(x,normpdfDouble(x/100.0));
        return normpdfCache.get(x);
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
            Individual father=individuals.get(getRandomInt(0,individuals.size()-1));
            Individual mother=individuals.get(getRandomInt(0,individuals.size()-1));
            while (father==mother)
            {
                mother=individuals.get(getRandomInt(0,individuals.size()-1));
            }
            Individual son = new Individual(Math.min(mother.length,father.length));
            for(int i=0;i<son.length;i++)
            {
                int choice=getRandomInt(0,1);
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
            Individual individual = individuals.get(getRandomInt(0, individuals.size() - 1));
            int count = (int)(individual.length*strength);
            while (count-- > 0)
            {
                individual.setGene(getRandomInt(0,individual.length-1),
                        getRandomInt(0,1));
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
        List<Question> subjects = getSubjects(param.getCourseID());
        List<Individual> individuals = new ArrayList<>();
        initIndividual(individuals,subjects,param, INIT_INDIVIDUAL_SIZE);
        for(int generation=1;generation<=50;generation++)
        {
            evaluate(individuals);
            printIndividuals(individuals,generation);
            eliminate(individuals);
            cross(individuals,50-individuals.size());
            variation(individuals,0.1,0.05);
            try {
                Thread.sleep(1000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Question> getSubjects(int courseID)
    {
        List<SubjectJson> subjectJsons = subjectClient.getSubjects(
                null,null,null,null,String.valueOf(courseID),null,null);
        return SubjectUtil.jsons2Subjects(subjectJsons);
    }
}
class Individual implements Comparable<Individual>
{
    public int length;
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
            genes[pos]=(byte)(genes[pos]|(1<<(7-offset)));
        }
        if(gene==0)
        {
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
