package org.mixed.exam.bank.strategy;

import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.service.IntelligentService;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class EvaluateStrategy
{
    public static EvaluateStrategy DIFF_EVALUATE_STRATEGY=new DifficultyEvaluateStrategy();
    public static EvaluateStrategy KEY_EVALUATE_STRATEGY=new KeyEvaluateStrategy();
    public static EvaluateStrategy DATE_EVALUATE_STRATEGY=new DateEvaluateStrategy();
    public static EvaluateStrategy DISTRIBUTION_EVALUATE_STRATEGY=new DistributionEvaluateStrategy();
    public static EvaluateStrategy[] strategies = new EvaluateStrategy[]{DIFF_EVALUATE_STRATEGY,
            KEY_EVALUATE_STRATEGY,DATE_EVALUATE_STRATEGY,DISTRIBUTION_EVALUATE_STRATEGY};
    public abstract void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects);
    public static List<Question> getSubjects(List<Question> questions, IntelligentService.Individual individual,
                                             Map<String,Integer> distributions){
        List<Question> res = new ArrayList<>();
        for(int i=0;i<questions.size();i++){
            Question q = questions.get(i);
            if(!distributions.containsKey(q.getType())){
                continue;
            }
            if(distributions.get(q.getType())==0){
                continue;
            }
        }
        return null;
    }
}
