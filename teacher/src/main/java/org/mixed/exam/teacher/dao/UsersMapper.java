package org.mixed.exam.teacher.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.auth.api.po.Users;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper {
    //查询当前用户信息
    Users getUsersDetail(String username);
}
