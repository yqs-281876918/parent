package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.pojo.vo.PaperItem;
import org.mixed.exam.bank.service.PaperItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PaperItemController
{
    @Autowired
    private PaperItemService paperItemService;
    @ResponseBody
    @GetMapping("/paper/items")
    public List<PaperItem> items(){
        return paperItemService.paperItems();
    }
}
