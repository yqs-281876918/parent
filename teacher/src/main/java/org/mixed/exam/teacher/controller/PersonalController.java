package org.mixed.exam.teacher.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class PersonalController
{
    @GetMapping("index")
    public String index()
    {
        return "index";
    }
}
