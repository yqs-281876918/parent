package org.mixed.exam.admin.controller;

import com.github.pagehelper.PageInfo;
import org.mixed.exam.admin.service.RoleService;
import org.mixed.exam.auth.api.po.Users;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @RequestMapping("/findAll")
    public PageInfo<Users> findAll(int pageNum, int pageSize){
//        String user=request.getParameter("username");
        PageInfo<Users> page=null;
        page=roleService.findAll(pageNum,pageSize);
        return page;
    }
    @RequestMapping("/updateMul")
    public int updateMul(String[] users){//删除
        int row=0;
        row=roleService.updateMul((users));
        return row;
    }
    @RequestMapping("/update")
    public int update(String username){
        int row=0;
        row=roleService.Update(username);
        return row;
    }
    @RequestMapping("/updateInfo")
    public int updateInfo(Users dto){
        int row=0;
        row=roleService.UpdateInfo(dto);
        return row;
    }
    @RequestMapping("/Insert")
    public int Insert(Users dto){
        int row=0;
        row=roleService.Insert(dto);
        return row;
    }
    @RequestMapping("/Search")
    public PageInfo<Users> Search(int pageNum, int pageSize,Users dto){
        PageInfo<Users> page=null;
        page=roleService.Search(pageNum,pageSize,dto);
        return page;
    }





}
