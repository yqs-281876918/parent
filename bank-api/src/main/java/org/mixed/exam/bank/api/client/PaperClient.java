package org.mixed.exam.bank.api.client;

import org.mixed.exam.bank.api.pojo.po.Paper;
import org.mixed.exam.bank.api.pojo.vo.PaperItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("bank-service")
public interface PaperClient
{
    @ResponseBody
    @GetMapping("bank/paper/items")
    List<PaperItem> items();
    @ResponseBody
    @GetMapping("bank/paper")
    Paper getByID(@RequestParam("id") String id);
}
