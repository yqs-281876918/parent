package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.service.PaperControlService;
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
    @PostMapping("/papercontrol/getAll")
    public List<Paper> getAll(){
        return paperControlService.getAll();
    }
    //取自己的试卷
    @ResponseBody
    @GetMapping("/papercontrol/getOwn")
    public List<Paper> getOwn(@RequestParam("teacherid") String teacherid){
        return paperControlService.getOwn(teacherid);
    }
    //取有权限管理的试卷
    @ResponseBody
    @GetMapping("/papercontrol/getControl")
    public List<Paper> getControl(@RequestParam("teacherid") String teacherid){
        return paperControlService.getControl(teacherid);
    }
    //封存
    @ResponseBody
    @PostMapping("/papercontrol/sealed")
    public void sealed(@RequestParam("id") String id){
        paperControlService.sealed(id);
    }
    //预览
    @ResponseBody
    @PostMapping("/papercontrol/showOne")
    public Paper showOne(@RequestParam("id") String id){
        return paperControlService.showOne(id);
    }
    //编辑
    @ResponseBody
    @PostMapping("/papercontrol/edit")
    public Paper edit(@RequestParam("id") String id){
        return paperControlService.edit(id);
    }
    //复制
    @ResponseBody
    @PostMapping("/papercontrol/copy")
    public void copy(@RequestParam("id") String id){
        paperControlService.copy(id);

    }
    //发布考试
    @ResponseBody
    @PostMapping("/papercontrol/push")
    public int push(@RequestParam("id") String id){
        return paperControlService.push(id);
    }
    //删除
    @ResponseBody
    @PostMapping("/papercontrol/delete")
    public void delete(@RequestParam("id") String id){
        paperControlService.delete(id);
    }
    //分配教师
    @ResponseBody
    @PostMapping("/papercontrol/assign")
    public int assign(@RequestParam("id") String id){
        return paperControlService.assign(id);
    }
}
