package org.mixed.exam.bank.strategy;

import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.service.IntelligentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributionEvaluateStrategy extends EvaluateStrategy
{
    @Override
    public void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects) {
        Map<String,Integer> distribution = new HashMap<>();
        for(String type : param.getDistribution().keySet()){
            distribution.put(type,param.getDistribution().get(type));
        }
        double score = 100.0;
        double unit = 100.0/param.getSubjectCount();
        for (int i=0;i<individual.length;i++){
            Question q = subjects.get(i);
            if(!distribution.containsKey(q.getType())){
                score-=unit;
                continue;
            }
            int count = distribution.get(q.getType());
            distribution.put(q.getType(),count-1);
        }
        for (String type : param.getDistribution().keySet()){
            int count = distribution.get(type);
            score-=Math.abs(count)*unit;
        }
        individual.fitness+=score>0?score:0;
    }
}
