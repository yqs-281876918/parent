package org.mixed.exam.teacher.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.teacher.dao.ClassMapper;
import org.mixed.exam.teacher.pojo.po.ChooseClass;
import org.mixed.exam.teacher.pojo.po.Class;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassService {
    @Resource
    private ClassMapper classMapper;
    //添加新班级
    public int addClass(Class clazz){
        return classMapper.addClass(clazz);
    }
    //找到最大的班级id
    public int selectMaxCno(){
        return classMapper.selectMaxCno();
    }
    //班级列表
    public List<Class> getAllClass(String creator){
        return classMapper.getClasses(creator);
    }
    //删除班级
    public int deleteClass(long cno){
        return classMapper.deleteClass(cno);
    }
    //老师进入班级详情页
    public PageInfo<ChooseClass> getClassDetail(int pageNum, int pageSize, long cno){
        PageInfo<ChooseClass> pageInfo = null;
        PageHelper.startPage(pageNum,pageSize);
        List<ChooseClass> chooseClassList = classMapper.getClassDetail(cno);
        pageInfo=new PageInfo<>(chooseClassList,5);
        return pageInfo;
    }
    //删除某学生
    public int deleteStudent(ChooseClass chooseClass){
        return classMapper.deleteStudent(chooseClass);
    }
}
