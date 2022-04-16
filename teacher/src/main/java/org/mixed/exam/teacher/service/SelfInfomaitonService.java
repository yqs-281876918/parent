package org.mixed.exam.teacher.service;

import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.teacher.dao.SelfInfomationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelfInfomaitonService {
    @Autowired
    private SelfInfomationDao selfInfomationDao;

    public Object getInfomation(String username) {
        return selfInfomationDao.getInfomation(username);
    }

    public int isUsed(String username) {
        return selfInfomationDao.isUsed(username);
    }

//    public void changeUserName(String username,String newusername) {
//        selfInfomationDao.changeUserName(username,newusername);
//    }

    public void changeEmail(String username, String newemail) {
        selfInfomationDao.changeEmail(username,newemail);
    }

    public void changeTelephone(String username, String newtelephone) {
        selfInfomationDao.changeTelephone(username,newtelephone);
    }

    public void changeRealName(String username, String newrealname) {
        selfInfomationDao.changeRealName(username,newrealname);
    }

    public void changeSex(String username, String newsex) {
        selfInfomationDao.changeSex(username,newsex);
    }

    public void changeAge(String username, String newage) {
        selfInfomationDao.changeAge(username,newage);
    }

    public void changePassword(String username, String newpassword) {
        selfInfomationDao.changePassword(username,newpassword);
    }

    public String getEncode(String o) {
        return selfInfomationDao.getEncode(o);
    }
}
