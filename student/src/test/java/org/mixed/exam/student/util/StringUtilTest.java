package org.mixed.exam.student.util;

import org.junit.jupiter.api.Test;
import org.mixed.exam.bank.api.pojo.po.exam.Answer;

import java.util.Map;

class StringUtilTest {

    @Test
    void jsonToMap() {
        Map<String,Object> map =
                StringUtil.jsonToMap("{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}");
        System.out.println(map);
    }
    @Test
    void jsonToObject(){
        Answer answer = StringUtil.json2Object("{\"subjectId\":\"62515c3d818f1466fa88b44c\",\"subjectType\":\"singleChoiceQuestion\",\"score\":-1,\"answerList\":[\"666\"}",Answer.class);
        System.out.println(answer);
    }
}
//{"subjectId":"62515c3d818f1466fa88b44c","subjectType":"singleChoiceQuestion","score":-1,"answerList":""}
//{"subjectId":"62515cf6818f1466fa88b450","subjectType":"completion","score":-1,"answerList":["",""]}