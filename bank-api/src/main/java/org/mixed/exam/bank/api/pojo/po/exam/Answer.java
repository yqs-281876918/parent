package org.mixed.exam.bank.api.pojo.po.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String subjectId;
    private String subjectType;
    private Integer score = -1;
    private AnswerDetail answerDetail;
}
