package org.mixed.exam.paper.strategy;

import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;
import org.mixed.exam.paper.service.IntelligentService;
import java.util.List;

public abstract class EvaluateStrategy
{
    public static EvaluateStrategy DIFF_EVALUATE_STRATEGY=new DifficultyEvaluateStrategy();
    public static EvaluateStrategy KEY_EVALUATE_STRATEGY=new KeyEvaluateStrategy();
    public static EvaluateStrategy DATE_EVALUATE_STRATEGY=new DateEvaluateStrategy();
    public static EvaluateStrategy COUNT_EVALUATE_STRATEGY=new CountEvaluateStrategy();
    public static EvaluateStrategy[] strategies = new EvaluateStrategy[]{DIFF_EVALUATE_STRATEGY,
            KEY_EVALUATE_STRATEGY,DATE_EVALUATE_STRATEGY,COUNT_EVALUATE_STRATEGY};
    public abstract void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects);
}
