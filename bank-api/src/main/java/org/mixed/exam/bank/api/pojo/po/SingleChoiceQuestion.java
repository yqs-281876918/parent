package org.mixed.exam.bank.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 单选题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleChoiceQuestion extends Question
{
    private String description="null";
    private List<String> options= Collections.singletonList("null");
    private String answer="null";

    @Override
    public String toString() {
        return super.toString()+
                "SingleChoiceQuestion{" +
                "description='" + description + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                '}';
    }
}