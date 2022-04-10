package org.mixed.exam.bank.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinationChoice extends Question
{
    private List<String> answers= Collections.singletonList("null");
    private Integer optionCount=0;
}
