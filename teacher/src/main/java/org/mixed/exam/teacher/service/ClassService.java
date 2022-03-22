package org.mixed.exam.teacher.service;

import org.mixed.exam.teacher.dao.ClassMapper;
import org.mixed.exam.teacher.pojo.po.Class;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClassService {
    @Resource
    private ClassMapper classMapper;
    public int addClass(Class clazz){
        return classMapper.addClass(clazz);
    }
}
