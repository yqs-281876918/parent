package org.mixed.exam.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.admin.pojo.dto.usersDto;
import org.mixed.exam.admin.pojo.po.users;

import java.util.List;

@Mapper
public interface RoleMapper {
    //查找
    List<users> findAll();
    //批量删除
    int updateMul(String[] users);
    //单个删除
    int update(String[] users);
    //编辑
    int UpdateInfo(usersDto dto);



}
