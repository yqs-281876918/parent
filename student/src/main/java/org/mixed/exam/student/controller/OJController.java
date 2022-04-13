package org.mixed.exam.student.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.student.service.OJService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OJController
{
    @Autowired
    private OJService ojService;

    /**
     * @param code 用户提交的代码文件
     * @param id 题目id（必须是程序题）
     * @param jwt 用户token
     * @return 一个整数型字符串,若为-1表示本次评测有错误(如语法错误,id不是程序题等),
     *         若大于等于0则表示本次代码通过的测试用例的个数
     */
    @ResponseBody
    @PostMapping("/oj/judge")
    public String test(String code, String id, @CookieValue("token")String jwt){
        return String.valueOf(ojService.judge(code,id,AuthUtil.parseUsername(jwt)));
    }
}
