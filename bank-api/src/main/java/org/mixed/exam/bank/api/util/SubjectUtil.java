package org.mixed.exam.bank.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.po.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectUtil
{
    static
    {
        ObjectMapper mapper = new ObjectMapper();
        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //在序列化时忽略值为 null 的属性
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略值为默认值的属性
        //mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        SubjectUtil.jsonMapper=mapper;
    }
    private static ObjectMapper jsonMapper;
    private static List<String> typeList;
    public static List<String> getTypeList()
    {
        if(typeList!=null)
        {
            return typeList;
        }
        typeList= Arrays.asList("singleChoiceQuestion", "multipleChoiceQuestion","completion","judgment",
                "comprehensiveQuestion", "programProblem","combinationChoice");
        return typeList;
    }
    private static List<String> typeListCHN;
    public static List<String> getTypeListCHN()
    {
        if(typeListCHN!=null)
        {
            return typeListCHN;
        }
        typeListCHN=Arrays.asList("单选题","多选题","填空题","判断题","综合题","程序题","组合选择题");
        return typeListCHN;
    }
    public static String typeCHN2ENG(String chn)
    {
        for (String s : typeListCHN) {
            if (chn.equals(s)) {
                return s;
            }
        }
        return "";
    }
    public static String typeENG2CHN(String eng)
    {
        for (String s : typeList) {
            if (eng.equals(s)) {
                return s;
            }
        }
        return "";
    }
    public static Class<? extends Question> getClassByType(String type)
    {
        switch (type)
        {
            case "singleChoiceQuestion":
                return SingleChoiceQuestion.class;
            case "multipleChoiceQuestion":
                return MultipleChoiceQuestion.class;
            case "completion":
                return Completion.class;
            case "judgment":
                return Judgment.class;
            case "comprehensiveQuestion":
                return ComprehensiveQuestion.class;
            case "programProblem":
                return ProgramProblem.class;
            case "combinationChoice":
                return CombinationChoice.class;
            default:
                return Question.class;
        }
    }
    public static SubjectJson subject2Json(Question q)
    {
        SubjectJson subjectJson = new SubjectJson();
        subjectJson.setType(q.getType());
        try {
            String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(q);
            subjectJson.setJson(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
            return subjectJson;
        }
    }
    public static Question json2Subject(SubjectJson subjectJson)
    {
        try {
            return jsonMapper.readValue(subjectJson.getJson(),getClassByType(subjectJson.getType()));
        } catch (IOException e) {
            return null;
        }
    }
    public static List<SubjectJson> subjects2Json2(List<Question> subjects)
    {
        List<SubjectJson> jsons = new ArrayList<>();
        for(Question q : subjects)
        {
            jsons.add(subject2Json(q));
        }
        return jsons;
    }
    public static List<Question> jsons2Subjects(List<SubjectJson> subjectJsons)
    {
        List<Question> subjects=new ArrayList<>();
        for(SubjectJson json : subjectJsons)
        {
            subjects.add(json2Subject(json));
        }
        return subjects;
    }
}
