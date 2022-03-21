package org.mixed.exam.paper.strategy;

import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;
import org.mixed.exam.paper.service.IntelligentService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyEvaluateStrategy extends EvaluateStrategy
{
    @Override
    public void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects) {
        List<String> keyIDs = param.getKeys();
        Set<String> keysSet = new HashSet<>(keyIDs);
        int hitCount=0;
        for(int i=0;i<individual.length;i++)
        {
            if(individual.getGene(i)==1)
            {
                Question q = subjects.get(i);
                if(keysSet.contains(q.getClass2ndID()))
                {
                    hitCount++;
                }
            }
        }
        double hitRate=hitCount*1.0/individual.getGeneCount();
        individual.fitness+=hitRate*100;
    }
}
