package org.mixed.exam.auth.api.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class users
{
    private String username;
    private String password;
    private String role;
    private String realName;
}

