package org.mixed.exam.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.student.dao.ChooseClassMapper;
import org.mixed.exam.student.pojo.po.ChooseClass;
import org.mixed.exam.student.pojo.po.Class;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChooseClassService {
    @Resource
    private ChooseClassMapper chooseClassMapper;
    //根据邀请码得到班级号
    public Class getCnoByInvitation(String invitation){
        return chooseClassMapper.getCnoByInvitation(invitation);
    }
    //加入班级
    public int addChooseClass(ChooseClass chooseClass){
        return chooseClassMapper.addChooseClass(chooseClass);
    }
    //查找所加入的全部班级
    public List<ChooseClass> selectAllClass(String sname){
        return chooseClassMapper.selectAllClass(sname);
    }
    //得到学生真实姓名
    public Users getRealName(String username){
        return chooseClassMapper.getRealName(username);
    }
    //学生退出班级
    public int quitClass(long cno ,String sname){
        return chooseClassMapper.quitClass(cno,sname);
    }
    //学生进入班级详情页
    public PageInfo<ChooseClass> getClassDetail(int pageNum,int pageSize,long cno){
        PageInfo<ChooseClass> pageInfo = null;
        PageHelper.startPage(pageNum,pageSize);
        List<ChooseClass> chooseClassList = chooseClassMapper.getClassDetail(cno);
        pageInfo=new PageInfo<>(chooseClassList,5);
        return pageInfo;
    }
}
