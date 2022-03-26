package org.mixed.exam.admin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.admin.mapper.RoleMapper;
import org.mixed.exam.admin.pojo.dto.usersDto;
import org.mixed.exam.admin.pojo.po.users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper rolemapper;

    public PageInfo<users> findAll(int pageNum, int pageSize){
        PageInfo<users> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<users> users= rolemapper.findAll();
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<>(users,4);
        return page;
    }

    public int updateMul(String[] users){
        int row=-1;
        row=rolemapper.updateMul(users);
        return row;
    }
    //单一选择删除
    public int Update(String username){
        int row=0;
        row=rolemapper.update(username);
        return row;
    }
    public int UpdateInfo(usersDto dto){
        int row=0;
        row=rolemapper.UpdateInfo(dto);
        return row;
    }
    public int Insert(usersDto dto){
        int row=0;
        row=rolemapper.Insert(dto);
        return row;
    }
    public PageInfo<users> Search(int pageNum, int pageSize,usersDto dto){
        PageInfo<users> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<users> users= rolemapper.Search();
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<>(users,4);
        return page;
    }
}
