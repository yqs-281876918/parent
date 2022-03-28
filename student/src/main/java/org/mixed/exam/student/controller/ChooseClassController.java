package org.mixed.exam.student.controller;


import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.student.pojo.po.ChooseClass;
import org.mixed.exam.student.pojo.po.Class;
import org.mixed.exam.student.service.ChooseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ChooseClassController {
    @Autowired
    private ChooseClassService classService;
    //加入班级
    @RequestMapping("/class/choose")
    public String chooseClassByInvitation(String invitation,
                                          HttpServletRequest request){
        Class clazz = classService.getCnoByInvitation(invitation);
        if (clazz!=null){
            long cno = clazz.getCno();
            ChooseClass chooseClass = new ChooseClass();
            chooseClass.setCno(cno);
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String jwt = cookie.getValue();
                    String userName = AuthUtil.parseUsername(jwt);
                    chooseClass.setSname(userName);
                }
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chooseClass.setData(dateFormat.format(date));
//            if (classService.addChooseClass(chooseClass)>0){
//                return "添加成功";
//            }else {
//                return "添加失败";
//            }
            try {
                classService.addChooseClass(chooseClass);
            }catch (Exception e){
                return "添加失败";
            }
            return "添加成功";
        }else {
            return "未找到班级";
        }
    }
    //查找所有加入的班级
    @RequestMapping("/class/findAll")
    public List<ChooseClass> selectAllClass(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String sname = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                sname = userName;
            }
        }
        return classService.selectAllClass(sname);
    }
    //学生退出班级
    @RequestMapping("/class/quit")
    public int quitClass(long cno,String sname){
        return classService.quitClass(cno,sname);
    }
}
