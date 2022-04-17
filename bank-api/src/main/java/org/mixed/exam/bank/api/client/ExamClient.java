package org.mixed.exam.bank.api.client;

import org.mixed.exam.bank.api.pojo.po.Exam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("bank-service")
public interface ExamClient
{
    @ResponseBody
    @PostMapping("/bank/exam/submit")
    void submit(@RequestParam("startTime") Long startTime,@RequestParam("testTime") Integer testTime,
                @RequestParam("lateTime") Integer lateTime,@RequestParam("submitTime") Integer submitTime,
                @RequestParam("paperID") String paperID,@RequestParam("classID") Integer classID,
                @RequestParam("antiSettings") String antiSettings,@RequestParam("examName") String examName,
                @RequestParam("introduce") String introduce);
    @ResponseBody
    @GetMapping("/bank/exam/list")
    List<Exam> list(@RequestParam("userName") String userName);
    @ResponseBody
    @GetMapping("bank/exam")
    Exam getByID(@RequestParam("id")Integer id);
}
