package org.mixed.exam.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mixed.exam.auth.pojo.UserInfo;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper
{
    UserInfo getUser(@Param("username") String username);
}
