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
        query.with(Sort.by(Sort.Direction.DESC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }

    public float[] getscores(Integer examId,String type){
        //先得到scorelist 每个题的满分
        String sql="SELECT scoreList FROM exam WHERE id="+examId+";";
        List<Map<String, Object>> scorelist=jdbcTemplate.queryForList(sql);
        //System.out.println(scorelist.get(0).get("scoreList"));
        String sl= String.valueOf(scorelist.get(0).get("scoreList"));
        String[] arr=sl.split(",");
        //找到参与考试的人
        Query query = Query.query(Criteria.where("examId").is(examId));
        List<ExamDetail> examDetails=mongoTemplate.find(query,ExamDetail.class,"examDetail");

        float[] score = new float[examDetails.size()+1];
        //i是人 j是题
        int begin = 0;
        int end =0;
        for(int i=0;i<examDetails.size();i++){
            int trueScore=0;
            List<Answer> a = examDetails.get(i).getAnswers();
            for(int j=0;j<a.size();j++){
                if(a.get(j).getSubjectType().equals(type)){
                    begin=j;
                    break;
                }
            }
            for(int k=begin;k<a.size();k++){
                if(!a.get(k).getSubjectType().equals(type)){
                    end=k;
                    break;
                }
                else{
                    end=a.size()-1;
                }
            }
            //System.out.println(begin);
            //System.out.println(end);
            for(int j=begin;j<end;j++){
                trueScore=trueScore+a.get(j).getScore();
            }
            score[i]=trueScore;
        }
        float sum=0;
        if(begin==end){
            if(arr[begin]!=null&&!"".equals(arr[begin])&&!"null".equals(arr[begin])){
                sum=sum+Float.parseFloat(arr[begin]);
            }
        }
        else if(begin<end){
            for(int i=begin;i<end&&i<arr.length;i++){
                if(arr[i]!=null&&!"".equals(arr[i])&&!"null".equals(arr[i])){
                    sum=sum+Float.parseFloat(arr[i]);
                }
                else{
                    sum=sum+0;
                }
            }
        }
        //System.out.println(sum);
        score[examDetails.size()]=sum;
        return score;
    }

}
