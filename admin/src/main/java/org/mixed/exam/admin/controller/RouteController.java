package org.mixed.exam.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class RouteController
{
    @GetMapping("/verify/list")
    public String need_verify_list()
    {
        return "verify/list.html";
    }
}
