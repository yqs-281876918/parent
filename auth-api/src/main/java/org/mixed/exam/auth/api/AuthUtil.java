package org.mixed.exam.auth.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Base64;

public class AuthUtil
{
    public static String parseUsername(String jwt)
    {
        String payload=jwt.split("\\.")[1];
        byte[] bytes = Base64.getDecoder().decode(payload);
        String json_str=new String(bytes);
        JSONObject json=JSONObject.parseObject(json_str);
        return (String)json.get("user_name");
    }
}
