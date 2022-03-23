package org.mixed.exam.admin.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.admin.pojo.po.users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    //查找
    List<users> findAll(String user);
    //删除
    int updateMul(String[] users);



}
