package org.mixed.exam.teacher.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.bank.api.pojo.po.Exam;


import java.util.List;

@Mapper
public interface AnalyseMapper {
    //查找所有符合条件的考试
    List<Exam> findAll(int classID);

    //删除考试
    int delete(int id);

    //查找考试 模糊查询
    List<Exam> Search(String examName);

}
