package org.mixed.exam.teacher.controller;

import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.service.AnalyseService;
import org.mixed.exam.teacher.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExcelController {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private AnalyseService analyseService;
    @ResponseBody
    @GetMapping(value = "excel/export/{id}",produces = "application/vnd.ms-excel")
    public byte[] export(@PathVariable("id") Integer examID){
        List<ExamDetail> list = analyseService.findStuList(examID);
        return excelService.listToExcel(list);
    }
    @ResponseBody
    @PostMapping("excel/import")
    public String import_(@RequestParam("examID")String id){
        return "";
    }
}
