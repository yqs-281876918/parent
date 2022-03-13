package org.mixed.exam.login.controller;

import org.mixed.exam.login.exception.AuthorizationFailureException;
import org.mixed.exam.login.service.AuthService;
import org.mixed.exam.login.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController
{
    @Autowired
    private AuthService authService;
    @PostMapping("sign_in")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password
            , HttpServletRequest request, HttpServletResponse response)
    {
        String gatewayHostPort= HttpUtil.getGatewayHostPort();
        try {
            String token = authService.getJwt(username,password);
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            cookie.setMaxAge(3600*5-60*10);//让cookie比token早一点过期
            response.addCookie(cookie);
            return HttpUtil.buildRedirectUrl(gatewayHostPort,"login/loginDispatcher");
        }catch (RuntimeException e)
        {
            //return "redirect:http://www.baidu.com";
            return HttpUtil.buildRedirectUrl(gatewayHostPort,"/login/common/error/login_failure.html");
        }
    }
    @GetMapping("sign_out")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        //1.从cookie里面查询
        Cookie[] cookies = request.getCookies();
        Cookie tokenCookie = null;
        for(int i=0;i<cookies.length;i++)
        {
            if(cookies[i].getName().equals("token"))
            {
                tokenCookie = cookies[i];
                break;
            }
        }
        if(tokenCookie!=null)
        {
            tokenCookie.setMaxAge(0);
            response.addCookie(tokenCookie);
        }
        String gatewayHostPort= HttpUtil.getGatewayHostPort();
        return HttpUtil.buildRedirectUrl(gatewayHostPort,"login/login.html");
    }
    //登陆后根据角色转发至对应首页
    @GetMapping("/loginDispatcher")
    public String loginDispatcher()
    {
        String gatewayHostPort= HttpUtil.getGatewayHostPort();
        return HttpUtil.buildRedirectUrl(gatewayHostPort,"login/success.html");
    }
}
