package org.mixed.exam.teacher.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.teacher.service.SelfInfomaitonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SelfInfomaitonController {
    @Autowired
    private SelfInfomaitonService selfInfomaitonService;

    @ResponseBody
    @RequestMapping("/selfInfomation/getInfomation")
    public Object getInfomation(@CookieValue("token")String token){
        String username=AuthUtil.parseUsername(token);

        return selfInfomaitonService.getInfomation(username);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/isUsed")
    public int isUsed(@RequestParam("userName")String username){
        return selfInfomaitonService.isUsed(username);
    }
//    @ResponseBody
//    @RequestMapping("/selfInfomation/changeUserName")
//    public void changeUserName(@RequestParam("userName")String username,
//                               @RequestParam("newUserName")String newusername){
//        selfInfomaitonService.changeUserName(username,newusername);
//    }
    @ResponseBody
    @RequestMapping("/selfInfomation/changeEmail")
    public void changeEmail(@RequestParam("userName")String username,
                               @RequestParam("newEmail")String newemail){
        selfInfomaitonService.changeEmail(username,newemail);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/changeTelephone")
    public void changeTelephone(@RequestParam("userName")String username,
                            @RequestParam("newTelephone")String newtelephone){
        selfInfomaitonService.changeTelephone(username,newtelephone);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/changeRealName")
    public void changeRealName(@RequestParam("userName")String username,
                            @RequestParam("newRealName")String newrealname){
        selfInfomaitonService.changeRealName(username,newrealname);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/changeSex")
    public void changeSex(@RequestParam("userName")String username,
                            @RequestParam("newSex")String newsex){
        selfInfomaitonService.changeSex(username,newsex);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/changeAge")
    public void changeAge(@RequestParam("userName")String username,
                            @RequestParam("newAge")String newage){
        selfInfomaitonService.changeAge(username,newage);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/changePassword")
    public void changePassword(@RequestParam("userName")String username,
                          @RequestParam("newPassword")String newpassword){
        selfInfomaitonService.changePassword(username,newpassword);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/getEncode")
    public String getEncode(@RequestParam("p")String o){
        return selfInfomaitonService.getEncode(o);
    }
    @ResponseBody
    @RequestMapping("/selfInfomation/match")
    public boolean match(@RequestParam("p")String o,
                        @RequestParam("realoldpassword")String realoldpassword){
        return selfInfomaitonService.match(o,realoldpassword);
    }
}
