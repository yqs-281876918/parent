package org.mixed.exam.teacher.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.teacher.dao.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class PersonalController
{
    @Autowired
    private UsersMapper usersMapper;
    @GetMapping("index")
    public String index()
    {
        return "index";
    }
    @ResponseBody
    @RequestMapping("/users/detail")
    public Users getUsersDetail(@CookieValue("token")String jwt){
        String teaName= AuthUtil.parseUsername(jwt);
        return usersMapper.getUsersDetail(teaName);
    }
}
