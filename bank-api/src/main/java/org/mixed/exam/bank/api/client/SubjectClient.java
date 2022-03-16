package org.mixed.exam.bank.api.client;

import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.vo.SubjectItem;
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
    @GetMapping("api/subject/count")
    Integer getCountByType(@RequestParam("type") String type,@RequestParam("courseID") String courseID);
}
