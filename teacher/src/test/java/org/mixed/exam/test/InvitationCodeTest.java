package org.mixed.exam.test;

import org.mixed.exam.teacher.dao.InvitationCode;

class InvitationCodeTest {
    private InvitationCode invitationCode;
    public static void main(String[] args){
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("id=%d\t code=%s", i, InvitationCode.getInviteCode(i)));
        }
    }
}