package org.mixed.exam.teacher.service;

import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.dao.ReviewPaperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewPaperService {
    @Autowired
    private ReviewPaperDao reviewPaperDao;

    public ExamDetail AutoReviewAndGet(String id){
        return reviewPaperDao.AutoReviewAndGet(id);
    }
}
