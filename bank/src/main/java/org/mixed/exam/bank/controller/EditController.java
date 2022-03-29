package org.mixed.exam.bank.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class EditController {
    @Autowired
    public EditService editService;

    @ResponseBody
    @PostMapping("/edit/getfrom")
    public void getFrom(@RequestParam("id") String id,
                        @RequestParam("foreWord") String foreWord,
                        @RequestParam("subjectIDs[][]") String[][] subjectIDs,
                        @RequestParam("courseID") String courseID,
                        @RequestParam("open") String open,
                        @RequestParam("difficulty") Integer difficulty){
        List<List<String>> subjects = JSON.parseObject(JSON.toJSONString(subjectIDs),
                new TypeReference<List<List<String>>>() {
                });

        System.out.println(foreWord+" "+subjects+" "+courseID+" "+open+" "+difficulty);
        if(open.equals("是")){
            boolean open1=true;
            editService.getFrom(id,foreWord,subjects,courseID,open1,difficulty);
        }else if(open.equals("否")){
            boolean open1=false;
            editService.getFrom(id,foreWord,subjects,courseID,open1,difficulty);
        }else {
            System.out.println("是否开放 error");
        }
    }

    @ResponseBody
    @PostMapping("/edit/getAllCourse")
    public List<Classification> getAllCourse() {
        return editService.getAllCourse();
    }

    @ResponseBody
    @PostMapping("/edit/getCourse")
    public Classification getCourse(@RequestParam("courseID") String id) {
        return editService.getCourse(id);
    }

    @ResponseBody
    @PostMapping("/edit/getQuestions")
    public List<Question> getQuestions(@RequestParam("course") String course,
                                       @RequestParam("type") String type) {
        return editService.getQuestions(course,type);
    }
//    @ResponseBody
//    @PostMapping("/edit/getQuestion")
//    public Classification getQuestion(@RequestParam("id") String id) {
//        return editService.getCourse(id);
//    }

    public <T> List<T> twoDArrayToList(T[][] twoDArray) {
        List<T> list = new ArrayList<T>();
        for (T[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }

}
