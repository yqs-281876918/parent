package org.mixed.exam.teacher.controller;

import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.pojo.vo.PaperItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class RouteController
{
    @Autowired
    private PaperClient paperClient;
    @GetMapping("exam/publish")
    public String exam_publish(Model model)
    {
        List<PaperItem> items = paperClient.items();
        model.addAttribute("items",items);
        return "exam/publish.html";
    }
}
