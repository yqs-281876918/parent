package org.mixed.exam.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

@Controller
public class TestController
{
    @ResponseBody
    @PostMapping("/test")
    public String test(HttpServletRequest request)
    {
        Map<String,String[]> map = request.getParameterMap();
        for(String key : map.keySet())
        {
            String[] strs=(String[])map.get(key);
            System.out.print(key+" ");
            for(String s:strs)
            {
                System.out.print(s+" ");
            }
            System.out.println();
        }
        Enumeration<String> headers = request.getHeaderNames();
        for (Iterator<String> it = headers.asIterator(); it.hasNext(); ) {
            String name = it.next();
            System.out.println(name + ":" + request.getHeader(name));
        }
        return "ok";
    }
}
