package org.mixed.exam.admin.controller;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.bank.api.util.SubjectUtil;
import org.mixed.exam.admin.service.ExamineExercisesService;
import org.mixed.exam.admin.service.SensitiveWordService;
import org.mixed.exam.admin.service.SimHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@PreAuthorize("hasAnyRole('ROLE_adm')")
@Controller
public class ExamineExercisesController {
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private SubjectClient subjectClient;
    @Autowired
    private SimHashService simHashService;
    @Autowired
    private ExamineExercisesService examineExercisesService;

    @ResponseBody
    @GetMapping("examineExercises/getExercise")
    public Question getExercises(@RequestParam("id") String id, @RequestParam("type") String type) {
        return SubjectUtil.json2Subject(subjectClient.getSubjectByID(id, type));
    }
    @ResponseBody
    @PostMapping("examineExercises/wordCheck")
    public boolean wordCheck(@RequestParam("id") String id,@RequestParam("type") String type) {
        System.out.println(id);
        return sensitiveWordService.wordCheck(id,type);
    }
    @ResponseBody
    @PostMapping("examineExercises/repeatCheck")
    public Question repeatCheck(@RequestParam("id") String id,@RequestParam("type") String type) throws IOException {
        System.out.println(id+" "+type);
        return simHashService.repeatCheck(id,type);
        //return sensitiveWordService.wordCheck(txt);
    }
    @ResponseBody
    @PostMapping("examineExercises/pass")
    public int pass(@RequestParam("id") String id,@RequestParam("type") String type){
        return subjectClient.passSubject(id,type);
    }

}
