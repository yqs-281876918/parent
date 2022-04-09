package org.mixed.exam.bank.api.pojo.po.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetail {
    @Id
    private String id;
    private String username; //用户名
    private Integer examId; //考试Id
    private List<Answer> answers; //学生作答情况
    private Integer totalScore = -1; //总分数
    private Integer antiCount = 0; //切屏次数
    private Integer finishReview = 0;//默认0表示没批完 1表示批完
}
