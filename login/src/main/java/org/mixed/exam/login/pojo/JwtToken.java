package org.mixed.exam.login.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtToken
{
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String scope;
    private String jti;
}
