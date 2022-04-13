package org.mixed.exam.student.dao;

import feign.Param;
import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.student.pojo.po.ChooseClass;
import org.mixed.exam.student.pojo.po.Class;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChooseClassMapper {
    //学生加入班级
    int addChooseClass(ChooseClass chooseClass);
    //根据邀请码得到班级号
    Class getCnoByInvitation(@Param("invitation") String invitation);
    //学生加入的所有班级信息
    List<ChooseClass> selectAllClass(@Param("sname") String sname);
    //学生退出班级
    int quitClass(long cno,String sname);
    //班级总人数
    long getCountStudent(long cno);
    //班级人员信息详情
    List<ChooseClass> getClassDetail(long cno);
    //得到用户真实姓名
    String getRealName(String username);
}
