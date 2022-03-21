package org.mixed.exam.paper.dao;

import org.mixed.exam.bank.api.util.SubjectUtil;
import org.mixed.exam.paper.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaperDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    //取出所有试卷
    public List<Paper> getAll(){
        return mongoTemplate.findAll(Paper.class,"paper");
    }
    //取出自己创建的试卷
    public List<Paper> getOwn(String teacherid){
        Query query = Query.query(Criteria.where("creator").is(teacherid));
        return mongoTemplate.find(query,Paper.class,"paper");
    }
    //取自己管理的试卷
    public List<Paper> getControl(String teacherid){
        return null;
    }
    //封存
    public void sealed(String id){
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("open",false);
        mongoTemplate.updateFirst(query, update, Paper.class,"paper");
    }
    //通过id取一个试卷
    public Paper getOne(String id){
        return mongoTemplate.findById(id,Paper.class,"paper");
    }
    //保存试卷
    public void savePaper(Paper paper){
        mongoTemplate.save(paper,"paper");
    }
    //删除试卷
    public void deletePaper(String id){
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Paper.class,"paper");
    }
    //分配教师
    public int assign(String id){
        return 0;
    }

}
