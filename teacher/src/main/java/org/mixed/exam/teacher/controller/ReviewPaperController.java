package org.mixed.exam.teacher.controller;

import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.service.ReviewPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                 @RequestParam("examid") Integer examid){
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
}
