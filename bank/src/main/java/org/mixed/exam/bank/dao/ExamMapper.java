package org.mixed.exam.bank.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.bank.pojo.po.Exam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamMapper
{
    void addExam(String startTime, Integer testTime, Integer lateTime, Integer submitTime,
                 String paperID,Integer classID,String antiSettings);
    List<Exam> list(String userName);
    Exam getByID(Integer id);
}
