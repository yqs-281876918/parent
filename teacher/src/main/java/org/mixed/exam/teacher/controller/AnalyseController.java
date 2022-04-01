package org.mixed.exam.teacher.controller;

import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.ExamDetail;
import org.mixed.exam.teacher.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/analyse")
public class AnalyseController {
    @Autowired
    AnalyseService analyseService;
    @RequestMapping("/findAll")
    public PageInfo<Exam> findAll(int pageNum, int pageSize){
        PageInfo<Exam> page=null;
        page=analyseService.findAll(pageNum,pageSize);
        return page;
    }
    @RequestMapping("/delete")
    public int delete(int[] ids){//删除
        int row=0;
        row=analyseService.delete((ids));
        return row;
    }
    @RequestMapping("/Search")
    public PageInfo<Exam> Search(int pageNum, int pageSize,String examName){
        PageInfo<Exam> page=null;
        page=analyseService.Search(pageNum,pageSize,examName);
        return page;
    }
    @RequestMapping("/count")
    public int count(Integer examId){
        return analyseService.count(examId);
    }
    @RequestMapping("/max")
    public ExamDetail max(Integer examId){
        return analyseService.max(examId);
    }
    @RequestMapping("/min")
    public ExamDetail min(Integer examId){
        return analyseService.min(examId);
    }

}
