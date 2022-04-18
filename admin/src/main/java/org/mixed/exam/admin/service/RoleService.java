package org.mixed.exam.admin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mixed.exam.admin.mapper.RoleMapper;
import org.mixed.exam.auth.api.po.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper rolemapper;

    public PageInfo<Users> findAll(int pageNum, int pageSize){
        PageInfo<Users> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<Users> users= rolemapper.findAll();
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
    //编辑
    public int UpdateInfo(Users dto){
        int row=0;
        row=rolemapper.UpdateInfo(dto);
        return row;
    }
    public int Insert(Users dto){
        int row=0;
        String password=(new BCryptPasswordEncoder()).encode(dto.getPassword());
        dto.setPassword(password);
        row=rolemapper.Insert(dto);
        return row;
    }
    public PageInfo<Users> Search(int pageNum, int pageSize,Users dto){
        PageInfo<Users> page=null;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //查询需要的数据
        List<Users> users= rolemapper.Search(dto);
        System.out.println(users.get(0).getRealName());
        //users表示页面中呈现的数据
        //4表示页码个数
        page=new PageInfo<>(users,4);
        return page;
    }
}
