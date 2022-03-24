package org.mixed.exam.admin.pojo.dto;

import org.mixed.exam.admin.pojo.po.users;

public class usersDto extends users {
    private String username;
    private String RealName;
    private String role;


    public String getRealName() {
        return RealName;
    }


    public void setRealName(String realName) {
        RealName = realName;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
}
