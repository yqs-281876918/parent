package org.mixed.exam.teacher.dao;


import org.mixed.exam.bank.api.pojo.po.exam.Answer;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.by;


@Repository
public class AnalyseDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //算出最高、最低、平均分、参加考试人数
    //总人数
    public List<ExamDetail> getAll(Integer examId){
        Query query = new Query(Criteria.where("examId").is(examId));
        query.with(Sort.by(Sort.Direction.DESC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }

    //最高分
    public List<ExamDetail> max(Integer examId){
        Query query = new Query(Criteria.where("examId").is(examId));
        query.with(Sort.by(Sort.Direction.DESC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
    public List<ExamDetail> min(Integer examId){
        Query query = new Query(Criteria.where("examId").is(examId));
        query.with(Sort.by(Sort.Direction.ASC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
    //找出某场考试的全部学生
    public List<ExamDetail> getStuList(Integer examId){
        Query query = Query.query(Criteria.where("examId").is(examId));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
    //
    public int[] getscores(Integer examId,String type){
        String sql="SELECT scoreList FROM exam WHERE id="+examId+";";
        List<Map<String, Object>> scorelist=jdbcTemplate.queryForList(sql);
        System.out.println(scorelist.get(0).get("String"));
        String sl= String.valueOf(scorelist.get(0).get("String"));
        String[] arr=sl.split(",");

        Query query = Query.query(Criteria.where("examId").is(examId));
        List<ExamDetail> examDetails=mongoTemplate.find(query,ExamDetail.class,"examDetail");


        int[] score = new int[examDetails.size()];
        for(int i=0;i<examDetails.size();i++){
            int trueScore=0;
            List<Answer> a = examDetails.get(i).getAnswers();
            for(int j=0;j<a.size();j++){
                if(a.get(j).getSubjectType().equals(type)){
                    //if(a.get(i).getScore()==Integer.valueOf(arr[i])){
                    trueScore=trueScore+a.get(j).getScore();
                }
            }
            score[i]=trueScore;
        }
        return score;
    }

}
