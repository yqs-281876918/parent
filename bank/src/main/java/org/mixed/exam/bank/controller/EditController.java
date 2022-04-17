package org.mixed.exam.bank.controller;

import org.mixed.exam.admin.api.client.ClassifyClient;
import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.service.PaperService;
import org.mixed.exam.bank.service.SubjectItemService;
import org.mixed.exam.bank.service.SubjectService;
import org.mixed.exam.bank.util.StringUtil;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EditController {
    @Autowired
    private PaperService paperService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClassifyClient classifyClient;
    @Autowired
    private SubjectItemService subjectItemService;
    @Autowired
    private SubjectDao subjectDao;

    @GetMapping("paper/edit")
    public String edit(Model model,@RequestParam("id") String id){
        Paper paper = paperService.getPaper(id);
        model.addAttribute("paper",paper);
        List<SubjectItem> subjectItems = new ArrayList<>();
        for(String s_id :paper.getSubjectIDs()){
            subjectItems.add(subjectItemService.getItem(s_id));
        }
        model.addAttribute("subjectItems",subjectItems);
        model.addAttribute("courses",classifyClient.findAllCourse());
        return "paper/edit.html";
    }
}
