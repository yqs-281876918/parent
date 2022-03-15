package org.mixed.exam.admin.api.client;

import org.mixed.exam.admin.api.pojo.Classification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("admin-service")
public interface ClassifyClient
{
    @ResponseBody
    @GetMapping("admin/api/course/all")
    List<Classification> findAllCourse();
    @ResponseBody
    @GetMapping("admin/api/knowledge/all")
    List<Classification> findAllKnowledge();
}
