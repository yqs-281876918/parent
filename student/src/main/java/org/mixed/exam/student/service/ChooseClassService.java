package org.mixed.exam.student.service;

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
    //学生退出班级
    public int quitClass(long cno ,String sname){
        return chooseClassMapper.quitClass(cno,sname);
    }
}
