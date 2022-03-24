package org.mixed.exam.admin.pojo.dto;

import org.mixed.exam.admin.pojo.po.users;

public class usersDto extends users {
    private String username;
    private String realName;
    private String role;


    public String getRealName() {
        return realName;
    }


    public void setRealName(String realName) {
        this.realName = realName;
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
