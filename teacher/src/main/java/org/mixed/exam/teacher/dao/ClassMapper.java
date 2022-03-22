package org.mixed.exam.teacher.dao;


import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.teacher.pojo.po.Class;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMapper {
    //添加新班级
    int addClass(Class clazz);
    //获取班级列表
    List<Class> getClasses(String creator);
}
