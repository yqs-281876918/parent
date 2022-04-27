package org.mixed.exam.admin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.vo.SubjectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RouteController
{
    @Autowired
    private SubjectClient subjectClient;

    @ResponseBody
    @RequestMapping("/verify/list")
    public PageInfo<SubjectItem> need_verify_list(int pageNum,int pageSize)
    {
        List<SubjectItem> items = subjectClient.getUnVerifySubjectItems();
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
        PageInfo<SubjectItem> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping("/verify/listByType")
    public PageInfo<SubjectItem> need_verify_list_by_type(int pageNum,int pageSize,String type)
    {
        List<SubjectItem> items = subjectClient.getUnVerifySubjectItemsByType(type);
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
        PageInfo<SubjectItem> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
//    @GetMapping("/verify/examine")
//    public String examine(Model model,String type, String id)
//    {
//        model.addAttribute("id",id);
//        model.addAttribute("type",type);
//        String json = subjectClient.getSubjectByID(id);
//        model.addAttribute("json",json);
//        return "verify/examine.html";
//    }
}
