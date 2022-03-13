package org.mixed.exam.bank;

import org.junit.jupiter.api.Test;
import org.mixed.exam.classify.api.client.ClassifyClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients(clients = {ClassifyClient.class})
class BankApplicationTests {

    @Test
    void contextLoads() {
    }

}
