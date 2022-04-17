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
        for(int i=0;i<subjects.size();i++){
            if(individual.getGene(i)==0){
                continue;
            }
            Question q = subjects.get(i);
            if(distribution.containsKey(q.getType())){
                distribution.put(q.getType(),distribution.get(q.getType())-1);
            }
        }
        double score=100;
        for(String key : distribution.keySet()){
            if(distribution.get(key)>0){
                score=0;
                break;
            }
        }
        individual.fitness+=score;
    }
}
