package org.mixed.exam.auth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfo
{
    private Integer id;
    private String username;
    private String password;
    private String role;
}
