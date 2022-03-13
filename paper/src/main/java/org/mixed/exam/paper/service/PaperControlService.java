package org.mixed.exam.paper.service;

import org.mixed.exam.paper.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperControlService {

    //取出所有试卷
    public List<Paper> getAll(){
        List<Paper> p=null;
        return p;
    }
    //封存
    public void sealed(String id){

    }
    //预览
    public Paper showOne(String id){
        return null;
    }
    //编辑
    public Paper edit(String id){
        return null;
    }
    //复制
    public void copy(String id){

    }
    //发布考试
    public void push(String id){

    }
    //删除
    public void delete(String id){

    }
    //分配教师
    public void assign(String id,String teacherid){

    }
}
