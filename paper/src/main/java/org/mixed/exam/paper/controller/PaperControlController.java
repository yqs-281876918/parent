package org.mixed.exam.paper.controller;

import org.mixed.exam.paper.pojo.po.Paper;
import org.mixed.exam.paper.service.PaperControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PaperControlController {
    @Autowired
    private PaperControlService paperControlService;

    //取出所有试卷
    @ResponseBody
    @GetMapping("paper/getAll")
    public List<Paper> getAll(){
        return paperControlService.getAll();
    }
    //取自己的试卷
    @ResponseBody
    @GetMapping("paper/getOwn")
    public List<Paper> getOwn(@RequestParam("teacherid") String teacherid){
        return paperControlService.getOwn(teacherid);
    }
    //取有权限管理的试卷
    @ResponseBody
    @GetMapping("paper/getControl")
    public List<Paper> getControl(@RequestParam("teacherid") String teacherid){
        return paperControlService.getControl(teacherid);
    }
    //封存
    @ResponseBody
    @PostMapping("paper/sealed")
    public void sealed(@RequestParam("id") String id){
        paperControlService.sealed(id);
    }
    //预览
    @ResponseBody
    @GetMapping("paper/showOne")
    public Paper showOne(@RequestParam("id") String id){
        return paperControlService.showOne(id);
    }
    //编辑
    @ResponseBody
    @PostMapping("paper/edit")
    public Paper edit(@RequestParam("id") String id){
        return paperControlService.edit(id);
    }
    //复制
    @ResponseBody
    @PostMapping("paper/copy")
    public void copy(@RequestParam("id") String id){
        paperControlService.copy(id);
    }
    //发布考试
    @ResponseBody
    @PostMapping("paper/push")
    public int push(@RequestParam("id") String id){
        return paperControlService.push(id);
    }
    //删除
    @ResponseBody
    @PostMapping("paper/delete")
    public void delete(@RequestParam("id") String id){
        paperControlService.delete(id);
    }
    //分配教师
    @ResponseBody
    @PostMapping("paper/assign")
    public int assign(@RequestParam("id") String id){
        return paperControlService.assign(id);
    }
}
