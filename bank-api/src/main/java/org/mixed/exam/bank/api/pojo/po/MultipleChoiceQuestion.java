package org.mixed.exam.bank.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 多选题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleChoiceQuestion extends Question {
    private List<String> answers= Collections.singletonList("null");
    private Integer optionCount=0;
}