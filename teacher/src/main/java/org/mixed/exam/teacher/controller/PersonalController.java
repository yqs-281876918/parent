package org.mixed.exam.teacher.controller;

import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.pojo.vo.PaperItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class PersonalController
{
    @GetMapping("personal/{username}")
    public String index(@PathVariable("username")String username, Model model)
    {
        return "index";
    }
}
