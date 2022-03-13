package org.mixed.exam.auth.service;

import org.mixed.exam.auth.dao.UserMapper;
import org.mixed.exam.auth.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        System.out.println("登陆认证中");
        UserInfo userInfo = userMapper.getUser(username);
        return new User(userInfo.getUsername(), userInfo.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+userInfo.getRole()));
    }
}
