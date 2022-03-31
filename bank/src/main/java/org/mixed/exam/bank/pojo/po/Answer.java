package org.mixed.exam.bank.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String subjectId;
    private Integer score = -1;
    private AnswerDetail answerDetail;
}
