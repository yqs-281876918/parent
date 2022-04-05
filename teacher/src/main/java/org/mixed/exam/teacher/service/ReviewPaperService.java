package org.mixed.exam.teacher.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.dao.ReviewPaperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewPaperService {
    @Autowired
    private ReviewPaperDao reviewPaperDao;

    public ExamDetail AutoReviewAndGet(String id){
        return reviewPaperDao.AutoReviewAndGet(id);
    }

    public PageInfo<ExamDetail> find(int pageNum, int pageSize, Integer examid) {
        PageInfo<ExamDetail> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<ExamDetail> exams= reviewPaperDao.find(examid);
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<ExamDetail>(exams,4);
        return page;
    }

    public PageInfo<ExamDetail> Search(int pageNum, int pageSize, String studentName, String antiCount) {
        PageInfo<ExamDetail> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<ExamDetail> exams= null;
        if(!studentName.equals("")&&!antiCount.equals("")){
            exams= reviewPaperDao.SearchDouble(studentName,antiCount);
        }else if(!studentName.equals("")&&antiCount.equals("")){
            exams= reviewPaperDao.Search(studentName);
        }else if(studentName.equals("")&&!antiCount.equals("")){
            exams= reviewPaperDao.SearchByAnti(antiCount);
        }else if(studentName.equals("")&&antiCount.equals("")){
            exams= reviewPaperDao.SearchAll();
        }else {
            System.out.println("error in double element");
        }
        //users表示页面中呈现的数据
        //4表示页码个数
        page= new PageInfo<>(exams, 4);
        return page;
    }

    public int delete(String[] ids){
        int row=-1;
        row=reviewPaperDao.delete(ids);
        return row;
    }
}
