package org.mixed.exam.student.util;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class StringUtil
{
    public static Map<String,Object> jsonToMap(String json)
    {
        return (Map<String, Object>) JSON.parse(json);
    }
}
