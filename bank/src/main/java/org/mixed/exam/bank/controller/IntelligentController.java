package org.mixed.exam.bank.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.service.IntelligentService;
import org.mixed.exam.bank.service.PaperService;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class IntelligentController
{
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private IntelligentService intelligentService;
    @ResponseBody
    @PostMapping("paper/build")
    public String build(@CookieValue("token")String jwt, String foreWord, String courseID,
                      @RequestParam("keys[]") List<String> keys, Double ex_avg, Double total,
                      HttpServletRequest request)
    {
        Paper paper = new Paper();
        paper.setCreator(AuthUtil.parseUsername(jwt));
        paper.setForeWord(foreWord);
        paper.setCourseID(courseID);
        IntelligentParam param = new IntelligentParam();
        param.setCourseID(courseID);
        param.setKeys(keys);
        param.setBeginDay("1900-00-00 00:00:00");
        param.setEndDay("2200-00-00 00:00:00");
        param.setDifficulty(ex_avg/total*5);
        Map<String,Integer> distribution=new HashMap<>();
        List<String> types= SubjectUtil.getTypeList();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for(String type : types){
            if(!parameterMap.containsKey(type)){
                continue;
            }
            String[] strings = parameterMap.get(type);
            distribution.put(type,Integer.valueOf(strings[0]));
        }
        param.setDistribution(distribution);
        List<String> subjectIDs = intelligentService.build(param);
        paper.setSubjectIDs(subjectIDs);
        paperDao.add(paper);
        return "200";
//        param.setDifficulty(4.2);
//        param.setCourseID("622dbb040913ed05f6402c8b");
//        param.setKeys(Arrays.asList("622dcb956fdcc85405ef85ad", "623074271aabf7331f816aad", "62304bf274a387398923fe3b"));
//        List<IntelligentParam.SubjectDistribution> distributions = new ArrayList<>();
//        distributions.add(new IntelligentParam.SubjectDistribution("singleChoiceQuestion",5));
//        distributions.add(new IntelligentParam.SubjectDistribution("readComprehension",3));
//        distributions.add(new IntelligentParam.SubjectDistribution("completion",8));
//        param.setDistribution(distributions);
//        param.setBeginDay("2008-05-20 00:00:00");
//        param.setEndDay("2019-05-20 00:00:00");
//        intelligentService.build(param);
    }
}
