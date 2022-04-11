package org.mixed.exam.teacher.controller;

import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/analyse")
public class AnalyseController {
    @Autowired
    AnalyseService analyseService;
    @RequestMapping("/findAll")
    public PageInfo<Exam> findAll(int pageNum, int pageSize){
        PageInfo<Exam> page=null;
        page=analyseService.findAll(pageNum,pageSize);
        return page;
    }
    @RequestMapping("/delete")
    public int delete(int[] ids){//删除
        int row=0;
        row=analyseService.delete((ids));
        return row;
    }
    @RequestMapping("/Search")
    public PageInfo<Exam> Search(int pageNum, int pageSize,String examName){
        PageInfo<Exam> page=null;
        page=analyseService.Search(pageNum,pageSize,examName);
        return page;
    }
    @RequestMapping("/count")
    public float[] count(Integer examId,Integer totalScore){
        float[] count=new float[9];
        float[] per=new float[5];
        count[0]=analyseService.count(examId);
        count[1]=analyseService.max(examId);
        count[2]=analyseService.min(examId);
        count[3]=analyseService.avg(examId);
        per=analyseService.percentage(examId,totalScore);
        int num=4;
        for(int i=0;i<per.length-1;i++){
            count[num]=per[i];
            num++;
        }
        return count;
    }
    @RequestMapping("/max")
    public float max(Integer examId){
        return analyseService.max(examId);
    }
    @RequestMapping("/min")
    public float min(Integer examId){
        return analyseService.min(examId);
    }
    @RequestMapping("/findStuList")
    public List<ExamDetail> findStuList(Integer examId){
        return analyseService.findStuList(examId);
    }
    @RequestMapping("/avg")
    public float avg(Integer examId){return analyseService.avg(examId);}
    @RequestMapping("/percentage")
    public float[] percentage(Integer examId,Integer totalScore){
       System.out.println(totalScore);
        return analyseService.percentage(examId,totalScore);
    }

    //填空题正确率和错误率
    @RequestMapping("/apart-percentage")
    public int[] single(Integer examId,String type){
        int[] scoreList=analyseService.getscores(examId,type);
        System.out.println(examId);
        System.out.println(type);
        return scoreList;
    }
    //导出
//    @RequestMapping("/courseScore/export.action")
//    @ResponseBody
//    public void export(String code, HttpServletRequest request, HttpServletResponse response){
//        String thecoursename = analyseService.getNameByCode(code);
//        Score score = new Score();
//        score.setCode(code);
//        List<CourseScore> courseScoreList = scoreDao.selectCourseScoreList(score);
//        ExportExcel<CourseScore> ee = new ExportExcel<CourseScore>();
//        String[] headers = {"编号", "学年", "学号", "姓名", "班级", "成绩"};
//        String fileName = thecoursename + "课程成绩表";
//        ee.exportExcel(headers, courseScoreList, fileName, response);
//
//    }
}
