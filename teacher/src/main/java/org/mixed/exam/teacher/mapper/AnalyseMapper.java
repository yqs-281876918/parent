package org.mixed.exam.teacher.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.teacher.pojo.po.exam;

import java.util.List;

@Mapper
public interface AnalyseMapper {
    //查找所有符合条件的考试
    List<exam> findAll();
    //删除考试
    int delete(int[] ids);
    //查找考试 模糊查询
    List<exam> Search(String examName);
}
