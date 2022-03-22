package org.mixed.exam.bank.strategy;

import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.service.IntelligentService;
import org.mixed.exam.bank.util.MathUtil;

import java.util.List;

public class CountEvaluateStrategy extends EvaluateStrategy
{

    @Override
    public void evaluate(IntelligentService.Individual individual, IntelligentParam param, List<Question> subjects) {
        int count = individual.getGeneCount();
        int expectCount=param.getCount();
        double deviation=Math.abs(count-expectCount)*1.0/expectCount;
        deviation*=5;//放大
        double score= MathUtil.normpdfInt((int)(deviation+0.5)*100);
        individual.fitness+=score;
    }
}
