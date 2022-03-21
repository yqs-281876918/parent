package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.service.IntelligentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class IntelligentController
{
    @Autowired
    private IntelligentService intelligentService;
    @GetMapping("build")
    public void build()
    {
        IntelligentParam param = new IntelligentParam();
        param.setDifficulty(4.2);
        param.setCourseID("622dbb040913ed05f6402c8b");
        param.setKeys(Arrays.asList("622dcb956fdcc85405ef85ad", "623074271aabf7331f816aad", "62304bf274a387398923fe3b"));
        List<IntelligentParam.SubjectDistribution> distributions = new ArrayList<>();
        distributions.add(new IntelligentParam.SubjectDistribution("singleChoiceQuestion",5));
        distributions.add(new IntelligentParam.SubjectDistribution("readComprehension",3));
        distributions.add(new IntelligentParam.SubjectDistribution("completion",8));
        param.setDistributions(distributions);
        param.setBeginDay("2008-05-20 00:00:00");
        param.setEndDay("2019-05-20 00:00:00");
        intelligentService.build(param);
    }
}
