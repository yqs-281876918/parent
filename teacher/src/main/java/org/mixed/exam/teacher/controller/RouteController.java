package org.mixed.exam.teacher.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.Paper;
import org.mixed.exam.bank.api.pojo.vo.PaperItem;
import org.mixed.exam.teacher.dao.ClassMapper;
import org.mixed.exam.teacher.pojo.po.Class;
import org.mixed.exam.teacher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class RouteController
{
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private PaperClient paperClient;
    @Autowired
    private SubjectClient subjectClient;
    @ResponseBody
    @RequestMapping("/exam/paper_list")
    public PageInfo<PaperItem> paper_list(int pageNum, int pageSize)
    {
        List<PaperItem> items = paperClient.items();
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
        PageInfo<PaperItem> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
    @GetMapping("exam/publish/{paperID}")
    public String publish(Model model, @CookieValue("token")String jwt, @PathVariable("paperID")String paperID)
    {
        String teaName= AuthUtil.parseUsername(jwt);
        List<Class> classes=classMapper.getClasses(teaName);
        Paper paper = paperClient.getByID(paperID);
        List<String> subjectIDs=paper.getSubjectIDs();
        List<Map<String,Object>> subjectsMap=new ArrayList<>();
        for(String id : subjectIDs)
        {
            subjectsMap.add(StringUtil.jsonToMap(subjectClient.getSubjectByID(id)));
        }
        model.addAttribute("subjects",subjectsMap);
        model.addAttribute("paperID",paperID);
        model.addAttribute("classes",classes);
        return "exam/publish.html";
    }
}
