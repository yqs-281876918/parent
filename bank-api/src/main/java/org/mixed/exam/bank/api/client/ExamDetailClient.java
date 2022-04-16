package org.mixed.exam.bank.api.client;

import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("bank-service")
public interface ExamDetailClient {
    @ResponseBody
    @PostMapping("/bank/test/exam-detail/save")
    void addExamDetail(ExamDetail examDetail);
}
