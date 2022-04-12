package org.mixed.exam.student.service;

import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.student.dao.ExamDetailDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExamDetailService {
    @Resource
    private ExamDetailDao examDetailDao;
    //保存学生答题卡
    public void saveExamDetail(ExamDetail examDetail){
        examDetailDao.saveExamDetail(examDetail);
    }
}
