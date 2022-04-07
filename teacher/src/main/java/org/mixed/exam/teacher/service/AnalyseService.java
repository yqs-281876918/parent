package org.mixed.exam.teacher.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.javassist.compiler.ast.StringL;
import org.apache.ibatis.jdbc.Null;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.exam.Answer;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.dao.AnalyseDao;
import org.mixed.exam.teacher.mapper.AnalyseMapper;
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

    public PageInfo<Exam> findAll(int pageNum, int pageSize){
        PageInfo<Exam> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<Exam> exams= analyseMapper.findAll();
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
    public int max(Integer examId){
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
    public int min(Integer examId){
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
    public int[] percentage(Integer examId,Integer totalScore,Integer personNum){
        int per1 = 0,per2=0,per3=0,per4=0 , per5=0;
        List<ExamDetail> examDetails = analyseDao.getAll(examId);

        System.out.println(examId);
        System.out.println(totalScore);

        for(int i=0;i<examDetails.size();i++){
            System.out.println(examDetails.get(i).getTotalScore());

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
        ////System.out.println(per1);
        int[] percentage={per1,per2,per3,per4,per5};
        //System.out.println(Arrays.toString(percentage));
        return percentage;
    }

    public int[] getscores(Integer examId,String type){
        //先得到每个题的满分
        return analyseDao.getscores(examId,type);
    }


}
