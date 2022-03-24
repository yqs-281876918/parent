package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaperApiController
{
    @Autowired
    private PaperDao paperDao;
    @ResponseBody
    @GetMapping("/paper")
    public Paper getByID(@RequestParam("id") String id)
    {
        return paperDao.getOne(id);
    }
}
