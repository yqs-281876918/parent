package org.mixed.exam.bank.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 综合题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ComprehensiveQuestion extends Question
{
    private String answer= "无参考答案";
}