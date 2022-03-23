package org.mixed.exam.admin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.admin.mapper.RoleMapper;
import org.mixed.exam.admin.pojo.po.users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper rolemapper;

    public PageInfo<users> findAll(int pageNum, int pageSize, String user){
        PageInfo<users> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<users> users= rolemapper.findAll(user);
        page=new PageInfo<>(users,4);
        return page;
    }

    public int updateMul(String[] users){
        int row=-1;
        row=rolemapper.updateMul(users);
        return row;
    }

}
