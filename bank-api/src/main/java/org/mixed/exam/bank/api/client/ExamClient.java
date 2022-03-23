package org.mixed.exam.bank.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("bank-service")
public interface ExamClient
{
    @ResponseBody
    @PostMapping("/bank/exam/submit")
    void submit(@RequestParam("startTime") String startTime,@RequestParam("testTime") Integer testTime,
                @RequestParam("lateTime") Integer lateTime,@RequestParam("submitTime") Integer submitTime,
                @RequestParam("paperID") String paperID,@RequestParam("classID") Integer classID,
                @RequestParam("antiSettings") String antiSettings);
}
