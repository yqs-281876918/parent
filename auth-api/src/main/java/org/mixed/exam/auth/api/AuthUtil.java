package org.mixed.exam.auth.api;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.mixed.exam.auth.api.exception.JwtParseFailException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class AuthUtil
{
    private static String secret="OhxVMm5Fjr%S!fq^Ss%O9JcMmo&iHM@q0h&FacVE";
    public static String parseUsername(String jwt)
    {
        try {
            Claims body = parseJwt(jwt);
            return body.get("user_name",String.class);
        }catch (JwtParseFailException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean checkIsExp(String jwt)
    {
        try {
            Claims body = parseJwt(jwt);
            long now = new Date().getTime();
            return now>body.getExpiration().getTime();
        }catch (JwtParseFailException e)
        {
            return true;
        }
    }
    public static List<String> parseAuthorities(String jwt)
    {
        try {
            Claims body = parseJwt(jwt);
            List<String> auths=(ArrayList<String>)body.get("authorities");
            return auths;
        }catch (JwtParseFailException e)
        {
            return null;
        }
    }
    private static Claims parseJwt(String jwt) throws JwtParseFailException {
        try {
            return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(jwt).getBody();
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new JwtParseFailException();
        }
    }
}
