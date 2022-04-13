package org.mixed.exam.teacher.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.service.ReviewPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.List;

@RestController
public class ReviewPaperController {
    @Autowired
    private ReviewPaperService reviewPaperService;

    @RequestMapping("/reviewpaper/autoandget")
    public ExamDetail AutoReviewAndGet(@RequestParam("id") String id){
        return reviewPaperService.AutoReviewAndGet(id);
    }
    @RequestMapping("/reviewpaper/find")
    public PageInfo<ExamDetail> find(@RequestParam("pageNum") int pageNum,
                                 @RequestParam("pageSize") int pageSize,
                                 @RequestParam("examid") int examid){
        PageInfo<ExamDetail> page=null;
        page=reviewPaperService.find(pageNum,pageSize,examid);
        return page;
    }
    @RequestMapping("/reviewpaper/Search")
    public PageInfo<ExamDetail> Search(@RequestParam("pageNum") int pageNum,
                                       @RequestParam("pageSize") int pageSize,
                                       @RequestParam("studentName") String studentName,
                                       @RequestParam("antiCount") String antiCount){
        PageInfo<ExamDetail> page=null;
        page=reviewPaperService.Search(pageNum,pageSize,studentName,antiCount);
        return page;
    }
    @RequestMapping("/reviewpaper/delete")
    public int delete(String[] ids){//删除
        int row=0;
        row=reviewPaperService.delete((ids));
        return row;
    }
    @RequestMapping("/reviewpaper/finishReview")
    public void finishReview(@RequestParam("id") String id){
        reviewPaperService.finishReview(id);
    }
    @RequestMapping("/reviewpaper/getScoreList")
    public String[] getScoreList(@RequestParam("id") String id){
        return reviewPaperService.getScoreList(id);
    }
    @RequestMapping("/reviewpaper/changeScore")
    public void changeScore(@RequestParam("id") String id,
                            @RequestParam("Answer") String subject){
        System.out.println(subject);
        reviewPaperService.changeScore(id,subject);
    }
    @RequestMapping("/reviewpaper/changeTotalScore")
    public void changeTotalScore(@RequestParam("id") String id,
                            @RequestParam("score") int totalscore){
        reviewPaperService.changeTotalScore(id,totalscore);
    }

}
