package org.mixed.exam.student;

import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients(clients = {ExamClient.class, PaperClient.class, SubjectClient.class})
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

}
