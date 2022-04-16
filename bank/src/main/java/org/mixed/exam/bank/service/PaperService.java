package org.mixed.exam.bank.service;

import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {
    @Autowired
    private PaperDao paperDao;
    //取出所有试卷
    public List<Paper> getAll(){
        return paperDao.getAll();
    }
    //取自己的试卷
    public List<Paper> getOwn(String teacherid){
        return paperDao.getOwn(teacherid);
    }
    //取自己有权限管理的试卷
    public List<Paper> getControl(String teacherid){
        return paperDao.getControl(teacherid);
    }
    //封存试卷
    public void sealed(String id){
        paperDao.sealed(id);
    }
    //开放试卷
    public void openPaper(String id){
        paperDao.openPaper(id);
    }
    //预览
    public Paper getPaper(String id){
        return paperDao.getOne(id);

    }
    //编辑
    public Paper edit(String id){
        return paperDao.getOne(id);
    }
    //复制
    public void copy(String id){
        Paper p=paperDao.getOne(id);
        Paper n=new Paper();
        String str=p.getForeWord();
        str="(副本)"+str;
        n.setForeWord(str);
        n.setCorrectCount(p.getCorrectCount());
        n.setCourseID(p.getCourseID());
        n.setCreator(p.getCreator());
        n.setDate(p.getDate());
        n.setDifficulty(p.getDifficulty());
        n.setOpen(p.getOpen());
        n.setRespondentCount(p.getRespondentCount());
        n.setSubjectIDs(p.getSubjectIDs());
        paperDao.savePaper(n);
    }
    //删除
    public void delete(String id){
        paperDao.deletePaper(id);
    }
    //分配教师
    public int assign(String id){
        return 0;
    }
}
