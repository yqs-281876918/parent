package org.mixed.exam.admin.controller;

import org.mixed.exam.admin.service.BanWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@PreAuthorize("hasAnyRole('ROLE_adm')")
@Controller
public class BanWordsController {
    @Autowired
    BanWordsService banWordsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @GetMapping("/test")
    public String test()
    {
        final String authorization = HttpHeaders.AUTHORIZATION;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader;
    }

    @ResponseBody
    @PostMapping("/banWords/addWord")
    public int addWord(@RequestParam("word")String word){
        return banWordsService.addWord(word);
    }
    @ResponseBody
    @GetMapping("/banWords/searchWord")
    public List<Map<String, Object>> serchWord(@RequestParam("word")String word){
        return banWordsService.serchWord(word);
    }
    @ResponseBody
    @PostMapping("/banWords/deleteWord")
    public int deleteWord(@RequestParam("word")String word){
        return banWordsService.deleteWord(word);
    }
    @ResponseBody
    @PostMapping("/banWords/changeWord")
    public int deleteWord(@RequestParam("id")String id,@RequestParam("word")String word) {
        return banWordsService.changeWord(id, word);
    }
}
