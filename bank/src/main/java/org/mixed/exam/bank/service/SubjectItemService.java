package org.mixed.exam.bank.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectItemService
{
    @Autowired
    private SubjectDao subjectDao;
    public List<SubjectItem> getItems()
    {
        List<SubjectItem> items=new ArrayList<>();
        List<String> typeList = SubjectUtil.getTypeList();
        for(String type : typeList)
        {
            List<? extends Question> list = subjectDao.getSubjectsByType(type);
            for(Question q : list)
            {
                items.add(new SubjectItem(q));
            }
        }
        return items;
    }
    public PageInfo<SubjectItem> getSubjectItems(Integer pageNum, Integer pageSize){
        List<SubjectItem> items=new ArrayList<>();
        List<String> typeList = SubjectUtil.getTypeList();
        for(String type : typeList)
        {
            List<? extends Question> list = subjectDao.getSubjectsByType(type);
            for(Question q : list)
            {
                items.add(new SubjectItem(q));
            }
        }
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
}
