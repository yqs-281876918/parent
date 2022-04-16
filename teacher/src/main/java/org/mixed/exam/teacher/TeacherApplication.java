package org.mixed.exam.teacher;

import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.client.ExamDetailClient;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = {PaperClient.class,ExamClient.class, SubjectClient.class, ExamDetailClient.class})
public class TeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}
