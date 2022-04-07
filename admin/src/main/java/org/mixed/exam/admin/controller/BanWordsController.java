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
//    @ResponseBody
//    @PostMapping("/banWords/addWord")
//    public int addWord(@RequestParam("word")String word){
//        return banWordsService.addWord(word);
//    }

//    @ResponseBody
//    @GetMapping("/banWords/serchWord")
//    public List<String> serchWord(@RequestParam("word")String word){
//        return banWordsService.serchWord(word);
//    }

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
    public int deleteWord(@RequestParam("id")String id,@RequestParam("word")String word){
        return banWordsService.changeWord(id,word);
    }



//    public List<Map<String, Object>> isItSame(@RequestParam("word")String word){
//        String sql = "SELECT * FROM verify WHERE word = '"+word+"'";
//        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
//        return list;
//    }

    /*@ResponseBody
    @PostMapping("/banWords/addWord")
    public int addWord(@RequestParam("word")String word){
        System.out.println(isItSame(word));
        if(isItSame(word).isEmpty()){
            //add
            String sql2="INSERT INTO verify VALUES ('"+word+"')";
            if(jdbcTemplate.update(sql2)>0){
                return 1;
            }else {
                return 0;
            }
        }else {
            return 0;
        }

    }
    @ResponseBody
    @GetMapping("/banWords/serchWord")
    public List<Map<String, Object>> serchWord(@RequestParam("word")String word){
        String sql = "SELECT * FROM verify WHERE word like '%"+word+"%'";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }
    @ResponseBody
    @PostMapping("/banWords/deleteWord")
    public int deleteWord(@RequestParam("word")String word){
        String sql = "DELETE FROM verify where word = ?";
        //String sql = "DELETE FROM verify WHERE word = '"+word+"'";
        //jdbcTemplate.update(sql,"lucy");
        Object[] args={word};
        System.out.println(jdbcTemplate.update(sql,args));
        if(true){
            return 1;
        }else {
            return 0;
        }
    }*/
}
