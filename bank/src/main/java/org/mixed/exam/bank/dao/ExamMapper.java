package org.mixed.exam.bank.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.bank.pojo.po.Exam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamMapper
{
    void addExam(Long startTime, Integer testTime, Integer lateTime, Integer submitTime,
                 String paperID,Integer classID,String antiSettings,String examName,
                 String introduce,String scoreList,Integer totalScore);
    List<Exam> list(String userName);
    Exam getByID(Integer id);
}
