package org.mixed.exam.admin.controller;

import com.ctc.wstx.util.StringUtil;
import com.github.pagehelper.PageInfo;
import net.sf.jsqlparser.Model;
import org.mixed.exam.admin.pojo.dto.usersDto;
import org.mixed.exam.admin.pojo.po.users;
import org.mixed.exam.admin.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public int update(String username){
        int row=0;
        row=roleService.Update(username);
        return row;
    }
    @RequestMapping("/updateInfo")
    public int updateInfo(usersDto dto){
        int row=0;
        row=roleService.UpdateInfo(dto);
        return row;
    }
    @RequestMapping("/Insert")
    public int Insert(usersDto dto){
        int row=0;
        row=roleService.Insert(dto);
        return row;
    }
    @RequestMapping("/Search")
    public PageInfo<users> Search(int pageNum, int pageSize,usersDto dto){
        PageInfo<users> page=null;
        page=roleService.Search(pageNum,pageSize,dto);
        return page;
    }





}
