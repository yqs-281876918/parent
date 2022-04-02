package org.mixed.exam.teacher.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.dao.AnalyseDao;
import org.mixed.exam.teacher.mapper.AnalyseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        int num=0;
        List<ExamDetail> examDetails = analyseDao.getAll(examId);
        for(ExamDetail examDetail : examDetails){
            if(examDetail.getTotalScore()!=-1){
                System.out.println(examDetail.getTotalScore());
                num++;
            }
        }
        System.out.println(num);
        return num;

    }
    public ExamDetail max(Integer examId){
        return analyseDao.max(examId);
    }
    public ExamDetail min(Integer examId){return analyseDao.min(examId);}

    public List<ExamDetail> findStuList(int pageNum, int pageSize, int examId){

        return AnalyseDao.getStuList(examId);}


}
