package org.mixed.exam.admin.controller;

import com.github.pagehelper.PageInfo;
import org.mixed.exam.admin.pojo.dto.usersDto;
import org.mixed.exam.admin.pojo.po.users;
import org.mixed.exam.admin.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @RequestMapping("/findAll")
    public PageInfo<users> findAll(int pageNum, int pageSize){
//        String user=request.getParameter("username");
        PageInfo<users> page=null;
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
    public int update(String[] users){
        int row=0;
        row=roleService.Update((users));
        return row;
    }
    @RequestMapping("/updateInfo")
    public int updateInfo(usersDto dto){
        int row=0;
        row=roleService.UpdateInfo(dto);
        return row;
    }


}
