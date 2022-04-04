package org.mixed.exam.teacher.controller;

import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.service.ReviewPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewPaperController {
    @Autowired
    private ReviewPaperService reviewPaperService;

    @RequestMapping("/reviewpaper/autoandget")
    public ExamDetail AutoReviewAndGet(@RequestParam("id") String id){
        return reviewPaperService.AutoReviewAndGet(id);
    }

}
