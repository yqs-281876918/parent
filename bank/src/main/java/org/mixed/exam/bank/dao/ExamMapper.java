package org.mixed.exam.bank.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExamMapper
{
    void addExam(String startTime, Integer testTime, Integer lateTime, Integer submitTime,
                 String paperID,Integer classID,String antiSettings);
}
