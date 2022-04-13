package org.mixed.exam.teacher.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OJServiceTest {

    @Autowired
    private OJService ojService;
    @Test
    void judge() {
        //ojService.judge("");
    }
    @Test
    void testTrim(){
        String s = "\r\n\r ok\na b\n \t  ";
        System.out.println("begin");
        System.out.print(s.trim());
        System.out.println("end");
    }
}