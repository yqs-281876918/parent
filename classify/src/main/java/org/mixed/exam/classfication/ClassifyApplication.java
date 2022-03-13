package org.mixed.exam.classfication;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = {SubjectClient.class})
public class ClassifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifyApplication.class, args);
    }

}
