package org.mixed.exam.student.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamMapper {
    //根据班级号得到我的考试列表
    public List<Exam> findExamByCno(long classID);
}
