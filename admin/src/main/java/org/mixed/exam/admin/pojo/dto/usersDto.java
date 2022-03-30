package org.mixed.exam.admin.pojo.dto;

import org.mixed.exam.admin.pojo.po.users;

public class usersDto extends users {
    private String username;
    private String realName;
    private String role;
    private String password;
    private String email;
    private int telephone;


    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getTelephone() {
        return telephone;
    }

    @Override
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


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
