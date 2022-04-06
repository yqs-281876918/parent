package org.mixed.exam.admin.controller;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.vo.SubjectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RouteController
{
    @Autowired
    private SubjectClient subjectClient;
    @GetMapping("/verify/list")
    public String need_verify_list(Model model)
    {
        List<SubjectItem> items = subjectClient.getUnVerifySubjectItems();
        model.addAttribute("items",items);
        return "verify/list.html";
    }
    @GetMapping("/verify/examine")
    public String examine(Model model,String type, String id)
    {
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        String json = subjectClient.getSubjectByID(id);
        model.addAttribute("json",json);
        return "verify/examine.html";
    }
}
