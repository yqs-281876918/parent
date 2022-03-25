package org.mixed.exam.student.service;

import org.mixed.exam.student.dao.ChooseClassMapper;
import org.mixed.exam.student.pojo.po.ChooseClass;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChooseClassService {
    @Resource
    private ChooseClassMapper chooseClassMapper;
    //根据邀请码得到班级号
    public long getCnoByInvitation(String invitation){
        return chooseClassMapper.getCnoByInvitation(invitation);
    }
    //加入班级
    public int addChooseClass(ChooseClass chooseClass){
        return chooseClassMapper.addChooseClass(chooseClass);
    }
}
