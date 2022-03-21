package org.mixed.exam.paper.strategy;

import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;
import org.mixed.exam.paper.service.IntelligentService;
import org.mixed.exam.paper.util.MathUtil;
import java.util.List;

public class DifficultyEvaluateStrategy extends EvaluateStrategy
{
    @Override
    public void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects)
    {
        double expectedDiff=param.getDifficulty();
        int totalDiff=0;
        for(int i=0;i<individual.length;i++)
        {
            if(individual.getGene(i)==1)
            {
                totalDiff += subjects.get(i).getDifficulty();
            }
        }
        double avgDiff=totalDiff*1.0/individual.getGeneCount();
        double deviation=Math.abs(avgDiff-expectedDiff)/expectedDiff;
        double score= MathUtil.normpdfInt((int)(deviation*100+0.5));
        individual.fitness+=score;
    }
}
