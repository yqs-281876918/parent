package org.mixed.exam.verify.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SensitiveWordServiceTest {
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Test
    public void test()
    {
        System.out.println(sensitiveWordService.jdbcTemplate);
    }
}