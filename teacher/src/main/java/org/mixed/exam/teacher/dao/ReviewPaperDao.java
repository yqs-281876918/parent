package org.mixed.exam.teacher.dao;

import org.mixed.exam.bank.pojo.po.Exam;
import org.mixed.exam.bank.pojo.po.MultipleChoiceQuestion;
import org.mixed.exam.bank.pojo.po.SingleChoiceQuestion;
import org.mixed.exam.bank.pojo.po.exam.Answer;
import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewPaperDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //自动批卷 获取id自动给出单选多选填空的成绩 并取出来
    public ExamDetail AutoReviewAndGet(String id){
        ExamDetail e=mongoTemplate.findById(id,ExamDetail.class,"examDetail");
        //获取每道题设置的分值
        Integer examid = e.getExamId();
        String sql="SELECT scoreList FROM exam WHERE id="+examid+";";
        List<Map<String, Object>> scorelist=jdbcTemplate.queryForList(sql);
        System.out.println(scorelist.get(0).get("String"));
        String sl= String.valueOf(scorelist.get(0).get("String"));
        String[] arr=sl.split(",");
        List<Answer> a=e.getAnswers();
        for(int i=0;i<a.size();i++){
            if(a.get(i).getSubjectType().equals("singleChoiceQuestion")){
                List<String> hisanswer=a.get(i).getAnswerList();
                SingleChoiceQuestion s=mongoTemplate.findById(a.get(i).getSubjectId(),SingleChoiceQuestion.class,"subjects");
                String trueanswer=s.getAnswer();
                if(trueanswer.equals(hisanswer.get(0))){
                    a.get(i).setScore(Integer.valueOf(arr[i]));
                }else{
                    a.get(i).setScore(0);
                }
            }else if(a.get(i).getSubjectType().equals("multipleChoiceQuestion")){
                List<String> hisanswer=a.get(i).getAnswerList();
                MultipleChoiceQuestion s=mongoTemplate.findById(a.get(i).getSubjectId(),MultipleChoiceQuestion.class,"subjects");
                String trueanswer= String.valueOf(s.getAnswers());
                if(trueanswer.equals(String.valueOf(hisanswer))){
                    a.get(i).setScore(Integer.valueOf(arr[i]));
                }else{
                    a.get(i).setScore(0);
                }
            }else if(a.get(i).getSubjectType().equals("singleChoiceQuestion")){
                List<String> hisanswer=a.get(i).getAnswerList();
                SingleChoiceQuestion s=mongoTemplate.findById(a.get(i).getSubjectId(),SingleChoiceQuestion.class,"subjects");
                String trueanswer=s.getAnswer();
                if(trueanswer.equals(hisanswer.get(0))){
                    a.get(i).setScore(Integer.valueOf(arr[i]));
                }else{
                    a.get(i).setScore(0);
                }
            }
        }
        return e;
    }
}
