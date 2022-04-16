package org.mixed.exam.teacher.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.javassist.compiler.ast.StringL;
import org.apache.ibatis.jdbc.Null;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.bank.api.pojo.po.exam.Answer;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.dao.AnalyseDao;
import org.mixed.exam.teacher.mapper.AnalyseMapper;
import org.mixed.exam.teacher.pojo.po.ChooseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;



@Service
public class AnalyseService {
    @Resource
    private AnalyseMapper analyseMapper;
    @Autowired
    private AnalyseDao analyseDao;

    public PageInfo<Exam> findAll(int pageNum, int pageSize,int classId){
        PageInfo<Exam> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<Exam> exams= analyseMapper.findAll(classId);
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<Exam>(exams,4);
        return page;
    }
    public int delete(int[] ids){
        int row=-1;
        row=analyseMapper.delete(ids);
        return row;
    }
    public PageInfo<Exam> Search(int pageNum, int pageSize,String examName){
        PageInfo<Exam> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<Exam> exams= analyseMapper.Search(examName);
        //users表示页面中呈现的数据
        //4表示页码个数
        page= new PageInfo<>(exams, 4);
        return page;
    }

    public int count(Integer examId){
        var num=0;
        List<ExamDetail> examDetails = analyseDao.getAll(examId);
        for(ExamDetail examDetail : examDetails){
            if(examDetail.getTotalScore()!=-1){
                //System.out.println(examDetail.getTotalScore());
                num++;
            }
        }
        //System.out.println(num);
        return num;
    }
    public float max(Integer examId){
        int max=0;
        List<ExamDetail> examDetails = analyseDao.max(examId);
        //System.out.println(examDetails.size());
        for(int i=0;i<examDetails.size();i++){
            max=examDetails.get(i).getTotalScore();
            //System.out.println(max);
            break;
        }
        return max;
    }
    public float min(Integer examId){
        int min=0;
        List<ExamDetail> examDetails = analyseDao.min(examId);
        for(int i=0;i<examDetails.size();i++){
            min=examDetails.get(i).getTotalScore();
            //System.out.println(min);
            break;
        }
        return min;
    }
    public float avg(Integer examId){
        float avg=0;
        float sum=0;
        int count=0;
        List<ExamDetail> examDetails = analyseDao.getAll(examId);
        for(int i=0;i<examDetails.size();i++){
            sum=sum+examDetails.get(i).getTotalScore();
            count++;
        }
        avg=sum/count;
        return avg;

    }

    public List<ExamDetail> findStuList(int examId){
        return analyseDao.getStuList(examId);
    }

    //计算各分数段人数比例
    public float[] percentage(Integer examId,Integer totalScore){
        float per1 = 0,per2=0,per3=0,per4=0 , per5=0;
        List<ExamDetail> examDetails = analyseDao.getAll(examId);
        //System.out.println(examId);
        //System.out.println(totalScore);
        for(int i=0;i<examDetails.size();i++){
            //System.out.println(examDetails.get(i).getTotalScore());
            int score=examDetails.get(i).getTotalScore();
            ///System.out.println(score);
            if(score!=-1){
                float per=(float)score/totalScore;
                if(per >= 0 && per <= 0.2){ per1++;}
                if (per > 0.2 && per <= 0.4) { per2++;}
                if (per > 0.4 && per <= 0.6) { per3++;}
                if (per > 0.6 && per <= 0.8) { per4++;}
                if (per > 0.8 && per <= 1) { per5++;}
            }
        }
        int num=count(examId);
        ////System.out.println(per1);
        float[] percentage={per1,per2,per3,per4,per5,num};
        //System.out.println(Arrays.toString(percentage));
        return percentage;
    }

    public int[] getscores(Integer examId,String type){
        //先得到每个人填空题的得分 + 填空题总分
        float[] list=analyseDao.getscores(examId,type);
        float sum=list[list.length-1];
        //System.out.println(sum);
        int per1=0,per2=0,per3=0,per4=0;
        for(int i=0;i<list.length-1;i++){
            float per= (float)( Math.round(list[i]*100/sum)/100.0);
            //System.out.println(per);
            if(per>=0&&per<=0.25){
                per1++;
            }
            if(per>0.25&&per<=0.50){
                per2++;
            }
            if(per>0.50&&per<=0.75){
                per3++;
            }
            if(per>0.75&&per<=1.00){
                per4++;
            }
        }
        int[] percentage1={per1,per2,per3,per4};
        return percentage1;
    }
    //查找一场考试所有题的题型 、 题号
    public PageInfo<Answer> getAllDetail(int pageNum, int pageSize,Integer examId){
        PageInfo<Answer> pageInfo = null;
        PageHelper.startPage(pageNum,pageSize);
        List<Answer> a = analyseDao.getAllDetail(examId);
        pageInfo=new PageInfo<Answer>(a,5);
        return pageInfo;
    }
    //按题型查找
    public PageInfo<Answer> getElseDetil(int pageNum, int pageSize, Integer examId,String type) {
        PageInfo<Answer> pageInfo = null;
        PageHelper.startPage(pageNum,pageSize);
        List<Answer> a = analyseDao.getElseDetail(examId,type);
        pageInfo=new PageInfo<Answer>(a,5);
        return pageInfo;
    }
    //根据题目id查找题目描述
    public String getDescription(String subjectId){
        return analyseDao.getDescription(subjectId);
    }


   // 每道题答对的人数
    public int getsingleRight(Integer examId, String subjectId) {
        return analyseDao.getsingleRight(examId,subjectId);
    }

    //判断批阅是否完成
    public int finish(Integer id) {
        return analyseDao.right(id);
    }

//    public PageInfo<Answer> getAllDetail(int pageNum, int pageSize,Integer examId){
//        PageInfo<Answer> pageInfo = null;
//        PageHelper.startPage(pageNum,pageSize);
//        List<Answer> a = analyseDao.getAllDetail(examId);
//        String[] id = new String[a.size()];
//        for(int i=0;i<a.size();i++){
//            id[i]= a.get(i).getSubjectId();
//        }
//        String[] q = analyseDao.getDescription(id);
//        pageInfo=new PageInfo<Answer>(a,5);
//        return pageInfo;
//    }


}
