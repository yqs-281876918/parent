package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.service.SubjectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm,ROLE_tea2')")
public class SubjectItemController
{
    @Autowired
    private SubjectItemService subjectItemService;
    @ResponseBody
    @GetMapping("subject/items")
    public List<SubjectItem> items()
    {
        return subjectItemService.getItems();
    }
}
