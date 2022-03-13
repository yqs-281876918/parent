package org.mixed.exam.verify;

import org.junit.jupiter.api.Test;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VerifyApplicationTests {
    @Autowired
    private SubjectClient subjectClient;
    @Test
    void contextLoads()
    {
    }

}
