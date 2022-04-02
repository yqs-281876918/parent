package org.mixed.exam.bank.api.pojo.po.exam;

import lombok.Data;

import java.util.List;

@Data
public abstract class AnswerDetail {
    public abstract List<String> getAnswerList();
}
