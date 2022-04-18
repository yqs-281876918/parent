package org.mixed.exam.bank.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.dao.SubjectDao;
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
    @Autowired
    private SubjectDao subjectDao;
    @ResponseBody
    @PostMapping("/paper/add")
    public String addPaper(@RequestParam("foreWord") String foreWord,
                           @RequestParam("subjectIDs") List<String> subjectIDs,
                           @RequestParam("courseID") String courseID,
                           @RequestParam("difficulty") Double difficulty,
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
    public PageInfo<Paper> getAll(int pageNum, int pageSize){
        List<Paper> items = paperService.getAll();
        //创建Page类
        Page page = new Page(pageNum, pageSize);
        //为Page类中的total属性赋值
        int total =items.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize,total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(items.subList(startIndex,endIndex));
        //以Page创建PageInfo
        PageInfo<Paper> pageInfo = new PageInfo<>(page);
        return pageInfo;
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
    public String copy(@RequestParam("id") String id){
        paperService.copy(id);
        return "success";
    }
    //封存
    @ResponseBody
    @RequestMapping("/paper/sealed")
    public String sealed(@RequestParam("id") String id){
        paperService.sealed(id);
        return "success";
    }
    //开放试卷
    @ResponseBody
    @RequestMapping("/paper/open")
    public String openPaper(@RequestParam("id") String id){
        paperService.openPaper(id);
        return "success";
    }
    //删除
    @ResponseBody
    @PostMapping("/paper/delete")
    public String delete(@RequestParam("id") String id){
        System.out.println(id);
        paperService.delete(id);
        return "success";
    }
    //分配教师
    @ResponseBody
    @PostMapping("/paper/assign")
    public int assign(@RequestParam("id") String id){
        return paperService.assign(id);
    }
    //根据课程号得到课程名
    @ResponseBody
    @RequestMapping("/paper/className")
    public Classification getCourseName(String id){
        Classification classification = subjectDao.getCourse(id);
        return classification;
    }
}
