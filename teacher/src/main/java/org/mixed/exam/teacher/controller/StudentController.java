package org.mixed.exam.teacher.controller;

import org.mixed.exam.teacher.entity.Student;
import org.mixed.exam.teacher.excel.ExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class StudentController {
//    @GetMapping("/export")
//    public void export(HttpServletResponse response){
//        ExcelUtils.exportTemplate(response,"用户表", Student.class);
//    }
}
