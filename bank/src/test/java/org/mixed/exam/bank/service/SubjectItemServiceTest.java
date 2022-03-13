package org.mixed.exam.bank.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubjectItemServiceTest {

    @Autowired
    private SubjectItemService subjectItemService;
    @Test
    public void getItems()
    {
        System.out.println(subjectItemService.getItems());
    }
}