package org.mixed.exam.login.service;

import org.mixed.exam.login.exception.AuthorizationFailureException;
import org.mixed.exam.login.pojo.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AuthService
{
    @Value("${security.oauth2.client.client-id}")
    private String clientID;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Autowired
    private RestTemplate restTemplate;
    public String getJwt(String username,String password) throws RuntimeException
    {
        //设置url
        String url="http://auth-service/oauth/token";
        //封装请求参数
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("grant_type","password");
        paramMap.add("scope","all");
        paramMap.add("username",username);
        paramMap.add("password",password);
        //封装请求头
        HttpHeaders headers=new HttpHeaders();
        headers.add("Cache-Control","no-cache");
        String authorization=clientID+":"+clientSecret;
        authorization = Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
        authorization = "Basic "+ authorization;
        headers.add("Authorization",authorization);
        //封装请求
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramMap, headers);
        //发送请求
        try {
            JwtToken token = restTemplate.postForObject(url,request,JwtToken.class);
            return token.getAccess_token();
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public boolean checkToken(String token)
    {
        String url="http://127.0.0.1:1901/oauth/token";
        return true;
    }
}
