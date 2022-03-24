package org.mixed.exam.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.admin.pojo.po.users;

import java.util.List;

@Mapper
public interface RoleMapper {
    //查找
    List<users> findAll();
    //删除
    int updateMul(String[] users);




}
