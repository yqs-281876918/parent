package org.mixed.exam.paper.service;

import org.mixed.exam.paper.dao.PaperDao;
import org.mixed.exam.paper.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperControlService {
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
    //封存
    public void sealed(String id){
        paperDao.sealed(id);
    }
    //预览
    public Paper showOne(String id){
        return paperDao.getOne(id);
    }
    //编辑
    public Paper edit(String id){
        return paperDao.getOne(id);
    }
    //复制
    public void copy(String id){
        Paper p=paperDao.getOne(id);
        String str=p.getForeWord();
        str="(副本)"+str;
        p.setForeWord(str);
        paperDao.savePaper(p);
    }
    //发布考试
    public int push(String id){
        return 0;
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
