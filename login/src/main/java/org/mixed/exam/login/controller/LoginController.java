package org.mixed.exam.login.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.login.dao.LoginMapper;
import org.mixed.exam.login.service.AuthService;
import org.mixed.exam.login.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController
{
    @Autowired
    private AuthService authService;
    @Autowired
    private LoginMapper loginMapper;
    @PostMapping("/reg")
    public String reg_submit(String username,String password1,String password2){
        List<Users> users = loginMapper.findUsers(username);
        if(users!=null&&users.size()>0){
            return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/login/error/reg_failure.html");
        }
        if(!password1.equals(password2)){
            return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/login/error/reg_failure.html");
        }
        loginMapper.insertUser(username,(new BCryptPasswordEncoder()).encode(password1));
        return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/login/sign_in");
    }
    @GetMapping("/reg")
    public String reg()
    {
        return "reg.html";
    }
    @PostMapping("sign_in")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password,
                        HttpServletResponse response)
    {
        try {
            String token = authService.getJwt(username,password);
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
            return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/login/sign_in");
        }catch (RuntimeException e)
        {
            return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/login/error/login_failure.html");
        }
    }
    @GetMapping("sign_out")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        String gatewayHostPort= HttpUtil.getGatewayHostPort();
        return HttpUtil.buildRedirectUrl(gatewayHostPort,"/login/sign_in");
    }
    //登陆后根据角色转发至对应首页
    @GetMapping("/sign_in")
    public String loginDispatcher(@CookieValue(value = "token",defaultValue = "")String token)
    {
        if (token.equals("")||AuthUtil.checkIsExp(token))
        {
            return "sign_in.html";
        }
        List<String> authorities = AuthUtil.parseAuthorities(token);
        String authority=authorities.get(0);
        String userName=AuthUtil.parseUsername(token);
        switch (authority)
        {
            case "ROLE_adm":
                return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/admin/index.html");
            case "ROLE_tea1":
            case "ROLE_tea2":
                return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/teacher/index");
            default:
                return HttpUtil.buildRedirectUrl(HttpUtil.getGatewayHostPort(),"/student/class/index.html");
        }
    }
}
