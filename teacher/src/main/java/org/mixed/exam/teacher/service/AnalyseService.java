package org.mixed.exam.teacher.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.teacher.mapper.AnalyseMapper;
import org.mixed.exam.teacher.pojo.po.exam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnalyseService {
    @Resource
    private AnalyseMapper analyseMapper;
    public PageInfo<exam> findAll(int pageNum, int pageSize){
        PageInfo<exam> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<exam> exams= analyseMapper.findAll();
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<>(exams,4);
        return page;
    }
    public int delete(int[] ids){
        int row=-1;
        row=analyseMapper.delete(ids);
        return row;
    }
    public PageInfo<exam> Search(int pageNum, int pageSize,String examName){
        PageInfo<exam> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<exam> exams= analyseMapper.Search(examName);
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<>(exams,4);
        return page;
    }

}
