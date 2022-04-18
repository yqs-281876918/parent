package org.mixed.exam.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.auth.api.po.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    //查找
    List<Users> findAll();
    //批量删除
    int updateMul(String[] users);
    //单个删除
    int update(String username);
    //编辑
    int UpdateInfo(Users dto);
    //添加
    int Insert(Users dto);
    //搜索
    List<Users> Search(Users dto);


}
