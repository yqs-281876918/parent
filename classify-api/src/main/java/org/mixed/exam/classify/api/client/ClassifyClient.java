package org.mixed.exam.classify.api.client;

import org.mixed.exam.classify.api.pojo.Classification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("classify-service")
public interface ClassifyClient
{
    @ResponseBody
    @GetMapping("classify/api/course/all")
    List<Classification> findAllCourse();
    @ResponseBody
    @GetMapping("classify/api/knowledge/all")
    List<Classification> findAllKnowledge();
}
