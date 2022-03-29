package org.mixed.exam.student.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void jsonToMap() {
        Map<String,Object> map =
                StringUtil.jsonToMap("{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}");
        System.out.println(map);
    }
}