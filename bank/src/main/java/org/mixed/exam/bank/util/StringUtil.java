package org.mixed.exam.bank.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.Map;

public class StringUtil
{
    static
    {
        ObjectMapper mapper = new ObjectMapper();
        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //在序列化时忽略值为 null 的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略值为默认值的属性
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        jsonMapper=mapper;
    }
    public static Map<String,Object> jsonToMap(String json)
    {
        return (Map<String, Object>) JSON.parse(json);
    }
    private static ObjectMapper jsonMapper;
    public static  <T> T json2Object(String json,Class<T> clazz)
    {
        try {
            return jsonMapper.readValue(json,clazz);
        } catch (IOException e) {
            return null;
        }
    }
}
