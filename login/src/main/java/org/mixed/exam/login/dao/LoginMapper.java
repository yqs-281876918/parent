package org.mixed.exam.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mixed.exam.auth.api.po.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginMapper
{
    List<Users> findUsers(String userName);
    void insertUser(String userName,String password);
}
