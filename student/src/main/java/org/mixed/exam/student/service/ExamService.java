package org.mixed.exam.student.service;

import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.student.dao.ExamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExamService {
    @Resource
    private ExamMapper examMapper;
    //根据班级号得到我的考试列表
    public List<Exam> getExamByCno (long classID){
        return examMapper.findExamByCno(classID);
    }
}
