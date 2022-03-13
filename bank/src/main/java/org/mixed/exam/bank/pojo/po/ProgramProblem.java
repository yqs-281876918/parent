package org.mixed.exam.bank.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 程序题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramProblem extends Question
{
    private String description="null";
    private String answer="null";
    private String prepositionCode="null";//前置代码
    private String postCode="null";//后置代码
}