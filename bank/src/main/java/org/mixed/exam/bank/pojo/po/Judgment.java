package org.mixed.exam.bank.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 判断题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judgment extends Question
{
    private String answer="null";
}