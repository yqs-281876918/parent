package org.mixed.exam.paper.controller;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.bank.api.util.SubjectUtil;
import org.mixed.exam.paper.pojo.po.Paper;
import org.mixed.exam.paper.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EditController {
    @Autowired
    public EditService editService;

    @Autowired
    private SubjectClient subjectClient;

    @ResponseBody
    @PostMapping("/edit/getfrom")
    public void getFrom(@RequestParam("id") String id,
                        @RequestParam("foreWord") String foreWord,
                        @RequestParam("subjectIDs") List<List<String>> subjectIDs,
                        @RequestParam("courseID") String courseID,
                        @RequestParam("open") boolean open,
                        @RequestParam("difficulty") Integer difficulty){
        System.out.println(foreWord+" "+subjectIDs+" "+courseID+" "+open+" "+difficulty);
        editService.getFrom(id,foreWord,subjectIDs,courseID,open,difficulty);
    }

//    @ResponseBody
//    @GetMapping("/papercontrol/getquestion")
//    public Question getExercises(@RequestParam("id") String id, @RequestParam("type") String type) {
//        return SubjectUtil.json2Subject(subjectClient.getSubjectByID(id, type));
//    }
}
