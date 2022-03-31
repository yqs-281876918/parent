package org.mixed.exam.teacher.controller;

import com.github.pagehelper.PageInfo;
import org.mixed.exam.teacher.pojo.po.exam;
import org.mixed.exam.teacher.service.AnalyseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/analyse")
public class AnalyseController {
    @Resource
    private AnalyseService analyseService;
    @RequestMapping("/findAll")
    public PageInfo<exam> findAll(int pageNum, int pageSize){
        PageInfo<exam> page=null;
        page=analyseService.findAll(pageNum,pageSize);
        return page;
    }
    @RequestMapping("/delete")
    public int delete(int[] ids){//删除
        int row=0;
        row=analyseService.delete((ids));
        return row;
    }
    @RequestMapping("/Search")
    public PageInfo<exam> Search(int pageNum, int pageSize,String examName){
        PageInfo<exam> page=null;
        page=analyseService.Search(pageNum,pageSize,examName);
        return page;
    }

}
