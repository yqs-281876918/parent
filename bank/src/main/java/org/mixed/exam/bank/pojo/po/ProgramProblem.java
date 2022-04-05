package org.mixed.exam.bank.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 程序题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramProblem extends Question
{
    private List<String> inputs= Collections.singletonList("null");
    private List<String> outputs = Collections.singletonList("null");
}