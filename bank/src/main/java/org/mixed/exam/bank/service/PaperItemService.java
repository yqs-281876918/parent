package org.mixed.exam.bank.service;

import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.pojo.po.Paper;
import org.mixed.exam.bank.pojo.vo.PaperItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperItemService
{
    @Autowired
    private PaperDao paperDao;
    public List<PaperItem> paperItems()
    {
        List<PaperItem> items=new ArrayList<>();
        List<Paper> papers = paperDao.getAll();
        for(Paper p : papers)
        {
            items.add(new PaperItem(p));
        }
        return items;
    }
}
