package org.mixed.exam.bank.dao;

import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.bank.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaperDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static String DOCUMENT_NAME="paper";
    public void add(Paper paper){
        mongoTemplate.insert(paper,DOCUMENT_NAME);
    }
    //取出所有试卷
    public List<Paper> getAll(){
        return mongoTemplate.findAll(Paper.class,DOCUMENT_NAME);
    }
    //取出自己创建的试卷
    public List<Paper> getOwn(String teacherid){
        Query query = Query.query(Criteria.where("creator").is(teacherid));
        return mongoTemplate.find(query,Paper.class,DOCUMENT_NAME);
    }
    //取自己管理的试卷
    public List<Paper> getControl(String teacherid){
        return null;
    }
    //封存试卷
    public void sealed(String id){
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("open",false);
        mongoTemplate.updateFirst(query, update, Paper.class,DOCUMENT_NAME);
    }
    //开放试卷
    public void openPaper(String id){
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("open",true);
        mongoTemplate.updateFirst(query, update, Paper.class,DOCUMENT_NAME);
    }
    //通过id取一个试卷
    public Paper getOne(String id){
        return mongoTemplate.findById(id,Paper.class,DOCUMENT_NAME);
    }
    //保存试卷
    public void savePaper(Paper paper){
        mongoTemplate.save(paper,DOCUMENT_NAME);
    }
    //删除试卷
    public void deletePaper(String id){
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Paper.class,DOCUMENT_NAME);
    }
    //分配教师
    public int assign(String id){
        return 0;
    }
    //根据课程id取
    public Classification getCourse(String id) {
        return mongoTemplate.findById(id,Classification.class,"classifications");
    }
    //根据课程名取
    public List<Classification> getCourseid(String course) {
        Query query = Query.query(Criteria.where("classifyName").is(course));
        return mongoTemplate.find(query,Classification.class,"classifications");
    }

    //新建空试卷并返回
    public Paper newPaper() {
        Paper p=new Paper();
        p.setSubjectIDs(null);
        mongoTemplate.save(p);
        return p;
    }
}
