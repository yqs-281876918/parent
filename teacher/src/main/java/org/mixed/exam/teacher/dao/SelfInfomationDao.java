package org.mixed.exam.teacher.dao;

import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SelfInfomationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object getInfomation(String username) {
        String sql = "select * from users where username='"+username+"'";
        List<Map<String, Object>> e = jdbcTemplate.queryForList(sql);
        System.out.println(e);
//        Users back = new Users();
//        back.setAge((Integer) e.get(0).get("age"));
//        back.setEmail((String) e.get(0).get("email"));
//        back.setRealName((String) e.get(0).get("realName"));
//        back.setSex((Integer) e.get(0).get("sex"));
//        back.setTelephone((String) e.get(0).get("telephone"));
//        back.setUsername((String) e.get(0).get("userName"));
        Object back=e.get(0);
        System.out.println("back="+back);

        return back;
    }

    public int isUsed(String username) {
        String sql = "select * from users where username='"+username+"'";
        List<Map<String, Object>> e = jdbcTemplate.queryForList(sql);
        if(e.size()>0){
            return 1;//重复
        }else{
            return 0;//不重复
        }
    }

//    public void changeUserName(String username ,String newusername) {
//        String sql = "update users set username='"+newusername+"' where username='"+username+"'";
//        jdbcTemplate.update(sql);
//    }

    public void changeEmail(String username, String newemail) {
        String sql = "update users set email='"+newemail+"' where username='"+username+"'";
        jdbcTemplate.update(sql);
    }

    public void changeTelephone(String username, String newtelephone) {
        String sql = "update users set telephone='"+newtelephone+"' where username='"+username+"'";
        jdbcTemplate.update(sql);
    }

    public void changeRealName(String username, String newrealname) {
        String sql = "update users set realName='"+newrealname+"' where username='"+username+"'";
        jdbcTemplate.update(sql);
    }

    public void changeSex(String username, String newsex) {
        String sql = "update users set sex='"+newsex+"' where username='"+username+"'";
        jdbcTemplate.update(sql);
    }

    public void changeAge(String username, String newage) {
        String sql = "update users set age='"+newage+"' where username='"+username+"'";
        jdbcTemplate.update(sql);
    }

    public void changePassword(String username, String newpassword) {
        String encode=(new BCryptPasswordEncoder()).encode(newpassword);
        String sql = "update users set password='"+encode+"' where username='"+username+"'";
        jdbcTemplate.update(sql);
    }

    public String getEncode(String o) {
        return (new BCryptPasswordEncoder()).encode(o);
    }

    public boolean match(String o, String realoldpassword) {
        return (new BCryptPasswordEncoder()).matches(o,realoldpassword);
    }
}
