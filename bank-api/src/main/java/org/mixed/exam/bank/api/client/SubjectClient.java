package org.mixed.exam.bank.api.client;

import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.vo.SubjectItem;
import org.mixed.exam.bank.api.util.HttpUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("bank-service")
public interface SubjectClient
{
    @ResponseBody
    @GetMapping("bank/api/subject/id")
    SubjectJson getSubjectByID(@RequestParam("id")String id, @RequestParam("type")String type);

    @ResponseBody
    @GetMapping("bank/api/subject/items")
    List<SubjectItem> items();

    @ResponseBody
    @GetMapping("bank/api/subjects/type")
    List<SubjectJson> subjects(@RequestParam("type")String type);

    @ResponseBody
    @PostMapping("bank/api/subject/pass")
    Integer passSubject(@RequestParam("id")String id,@RequestParam("type")String type);

    @ResponseBody
    @GetMapping("bank/api/subject/count")
    Integer getCountByType(@RequestParam("type") String type,@RequestParam("courseID") String courseID);

    @ResponseBody
    @GetMapping("bank/api/subject/typeList")
    List<String> getTypeList(@RequestParam("language")String language);

    @ResponseBody
    @GetMapping("bank/api/subjects")
    List<SubjectJson> getSubjects(@RequestParam(value = "type",defaultValue = HttpUtil.NULL_STRING_VALUE)String type,
                                        @RequestParam(value = "open",defaultValue = HttpUtil.NULL_STRING_VALUE)String open,
                                        @RequestParam(value = "isExamined",defaultValue = HttpUtil.NULL_STRING_VALUE)String isExamined,
                                        @RequestParam(value = "difficulty",defaultValue = HttpUtil.NULL_STRING_VALUE)String difficulty,
                                        @RequestParam(value = "courseID",defaultValue = HttpUtil.NULL_STRING_VALUE)String courseID,
                                        @RequestParam(value = "class2ndID",defaultValue = HttpUtil.NULL_STRING_VALUE)String class2ndID,
                                        @RequestParam(value = "creator",defaultValue = HttpUtil.NULL_STRING_VALUE)String creator);
    @ResponseBody
    @GetMapping("bank/subject/json")
    String getSubjectByID(@RequestParam("id") String id);
}
