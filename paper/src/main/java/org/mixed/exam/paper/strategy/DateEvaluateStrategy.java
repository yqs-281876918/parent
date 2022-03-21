package org.mixed.exam.paper.strategy;

import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;
import org.mixed.exam.paper.service.IntelligentService;

import java.util.List;

public class DateEvaluateStrategy extends EvaluateStrategy{
    @Override
    public void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects)
    {
        long from = param.getBegin_day();
        long to = param.getEnd_day();
        int hitCount=0;
        for(int i=0;i<individual.length;i++)
        {
            if(individual.getGene(i)==1)
            {
                long now=subjects.get(i).getDate().getTime();
                if(isDateBetween(now,from,to))
                {
                    hitCount++;
                }
            }
        }
        double hitRate=hitCount*1.0/individual.getGeneCount();
        individual.fitness+=hitRate*100;
    }
    private boolean isDateBetween(long now,long from,long to)
    {
        if(to<from)
        {
            return false;
        }
        return now>=from&&now<=to;
    }
}
