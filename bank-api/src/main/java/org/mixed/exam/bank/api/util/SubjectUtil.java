package org.mixed.exam.bank.api.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.po.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SubjectUtil
{
    private static ObjectMapper jsonMapper;
    static {
        jsonMapper=new ObjectMapper();
        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //在序列化时忽略值为 null 的属性
        //jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略值为默认值的属性
        //jsonMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
    }
    public static Question json2Subject(SubjectJson subjectJson)
    {
        try {
            return jsonMapper.readValue(subjectJson.getJson(),getClassByType(subjectJson.getType()));
        } catch (IOException e) {
            Question errorSubject = new Question();
            errorSubject.setIntroduction("反序列化失败");
            return errorSubject;
        }
    }
    private static List<String> typeList;
    public static List<String> getTypeList()
    {
        if(typeList!=null)
        {
            return typeList;
        }
        typeList= Arrays.asList("singleChoiceQuestion", "multipleChoiceQuestion","completion","judgment","nounParsing",
                "calculationProblem","entryProblem","essayQuestion","dataItems","rankingQuestion","voteTopic",
                "clozeTest","readComprehension","listeningQuestion","comprehensiveQuestion","oralTopic",
                "programProblem","matching");
        return typeList;
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
            case "nounParsing":
                return NounParsing.class;
            case "calculationProblem":
                return CalculationProblem.class;
            case "entryProblem":
                return EntryProblem.class;
            case "essayQuestion":
                return EssayQuestion.class;
            case "dataItems":
                return DataItems.class;
            case "rankingQuestion":
                return RankingQuestion.class;
            case "voteTopic":
                return VoteTopic.class;
            case "clozeTest":
                return ClozeTest.class;
            case "readComprehension":
                return ReadComprehension.class;
            case "listeningQuestion":
                return ListeningQuestion.class;
            case "comprehensiveQuestion":
                return ComprehensiveQuestion.class;
            case "oralTopic":
                return OralTopic.class;
            case "programProblem":
                return ProgramProblem.class;
            case "matching":
                return Matching.class;
            default:
                return Question.class;
        }
    }
    public static String subject2Json(Question q)
    {
        String json="{}";
        try {
            json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(q);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
            return json;
        }
    }
}
