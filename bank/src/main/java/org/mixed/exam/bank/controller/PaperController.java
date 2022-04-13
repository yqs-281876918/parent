package org.mixed.exam.bank.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class PaperController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperDao paperDao;
    @ResponseBody
    @PostMapping("/paper/add")
    public String addPaper(@RequestParam("foreWord") String foreWord,
                           @RequestParam("subjectIDs") List<String> subjectIDs,
                           @RequestParam("courseID") String courseID,
                           @RequestParam("difficulty") Integer difficulty,
                           @CookieValue("token")String token){
        Paper paper=new Paper();
        paper.setForeWord(foreWord);
        paper.setSubjectIDs(subjectIDs);
        paper.setCourseID(courseID);
        paper.setDifficulty(difficulty);
        paper.setOpen(true);
        paper.setDate(new Date());
        paper.setCreator(AuthUtil.parseUsername(token));
        paperDao.add(paper);
        return "添加成功";
    }
    //取出所有试卷
    @ResponseBody
    @PostMapping("/paper/getAll")
    public List<Paper> getAll(){
        return paperService.getAll();
    }
    //取自己的试卷
    @ResponseBody
    @GetMapping("/paper/getOwn")
    public List<Paper> getOwn(@RequestParam("teacherid") String teacherid){
        return paperService.getOwn(teacherid);
    }
    //取有权限管理的试卷
    @ResponseBody
    @GetMapping("/paper/getControl")
    public List<Paper> getControl(@RequestParam("teacherid") String teacherid){
        return paperService.getControl(teacherid);
    }
    //复制
    @ResponseBody
    @PostMapping("/paper/copy")
    public void copy(@RequestParam("id") String id){
        paperService.copy(id);
    }
    //删除
    @ResponseBody
    @PostMapping("/paper/delete")
    public void delete(@RequestParam("id") String id){
        paperService.delete(id);
    }
    //分配教师
    @ResponseBody
    @PostMapping("/paper/assign")
    public int assign(@RequestParam("id") String id){
        return paperService.assign(id);
    }
}
