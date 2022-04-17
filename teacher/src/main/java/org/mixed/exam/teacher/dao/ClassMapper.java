package org.mixed.exam.teacher.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.teacher.pojo.po.ChooseClass;
import org.mixed.exam.teacher.pojo.po.Class;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMapper {
    //添加新班级
    int addClass(Class clazz);
    //获取最大的班级id
    int selectMaxCno();
    //获取班级列表
    List<Class> getClasses(String creator);
    //删除班级
    int deleteClass(@Param("cno") long cno);
    //获取班级详细学生信息
    List<ChooseClass> getClassDetail(long cno);
    //删除某学生
    int deleteStudent(ChooseClass chooseClass);
    //获取班级学生列表
    List<Users> stuList(Integer classID);
}
