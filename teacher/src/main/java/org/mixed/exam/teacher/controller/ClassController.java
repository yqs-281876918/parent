package org.mixed.exam.teacher.controller;


import com.github.pagehelper.PageInfo;
import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.teacher.dao.InvitationCode;
import org.mixed.exam.teacher.pojo.po.ChooseClass;
import org.mixed.exam.teacher.pojo.po.Class;
import org.mixed.exam.teacher.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ClassController {
//    @Autowired
//    private InvitationCode invitationCode;
    @Autowired
    private ClassService classService;
    //添加班级
    @RequestMapping("/class/addClass")
    public String addClass(Class clazz,
                        HttpServletRequest request){
        long cno = classService.selectMaxCno()+1;
        clazz.setCno(cno);
        String invitation = InvitationCode.getInviteCode(cno);
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
    //展示班级列表
    @RequestMapping("/class/getAllClass")
    public List<Class> getAllClass(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String creator = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                creator = userName;
            }
        }
        return classService.getAllClass(creator);
    }
    //删除班级
    @RequestMapping("/class/deleteClass")
    public int deleteClass(Long cno){
        return classService.deleteClass(cno);
    }
    //班级详情所有学生
    @RequestMapping("/class/detail")
    public PageInfo<ChooseClass> getClassDetail(int pageNum, int pageSize, long cno){
        return classService.getClassDetail(pageNum,pageSize,cno);
    }
    //删除某学生
    @RequestMapping("/class/deleteStudent")
    public int deleteStudent(ChooseClass chooseClass){
        return classService.deleteStudent(chooseClass);
    }
}
