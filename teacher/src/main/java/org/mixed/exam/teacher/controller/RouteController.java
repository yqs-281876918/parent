package org.mixed.exam.teacher.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.pojo.vo.PaperItem;
import org.mixed.exam.teacher.dao.ClassMapper;
import org.mixed.exam.teacher.pojo.po.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class RouteController
{
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private PaperClient paperClient;
    @GetMapping("exam/paper_list")
    public String paper_list(Model model)
    {
        List<PaperItem> items = paperClient.items();
        System.out.println(items);
        model.addAttribute("items",items);
        return "exam/paper_list.html";
    }
    @GetMapping("exam/publish/{paperID}")
    public String publish(Model model, @CookieValue("token")String jwt, @PathVariable("paperID")String paperID)
    {
        String teaName= AuthUtil.parseUsername(jwt);
        List<Class> classes=null;
        model.addAttribute("paperID",paperID);
        model.addAttribute("classes",classes);
        return "exam/publish.html";
    }
}
