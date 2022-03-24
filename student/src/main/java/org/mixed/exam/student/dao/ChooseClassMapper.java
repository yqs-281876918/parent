package org.mixed.exam.student.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mixed.exam.student.pojo.po.ChooseClass;

@Mapper
//@Repository
public interface ChooseClassMapper {
    //学生加入班级
    int addChooseClass(ChooseClass chooseClass);
    //根据邀请码得到班级号
    long getCnoByInvitation(@Param("invitation") String invitation);
}
