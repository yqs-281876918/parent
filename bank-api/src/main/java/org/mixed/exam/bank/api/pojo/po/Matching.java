package org.mixed.exam.bank.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 连线题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matching extends Question
{
    private String description="null";
    private List<String> optionsLeft = Collections.singletonList("null");
    private List<String> optionsRight = Collections.singletonList("null");
    private List<String> answer=Collections.singletonList("null");
}