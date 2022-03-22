package org.mixed.exam.teacher.controller;


import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.teacher.dao.InvitationCode;
import org.mixed.exam.teacher.pojo.po.Class;
import org.mixed.exam.teacher.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ClassController {
//    @Autowired
//    private InvitationCode invitationCode;
    @Autowired
    private ClassService classService;

    @RequestMapping("/class/addClass")
    public String addClass(Class clazz,
                        HttpServletRequest request){
        String invitation = InvitationCode.getInviteCode(clazz.getCno());
        clazz.setInvitation(invitation);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                clazz.setCreator(userName);
            }
        }
        if(classService.addClass(clazz)==1){
            return invitation;
        }else {
            return null;
        }
    }
}
