package org.mixed.exam.bank.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDto
{
    private String to;
    private String subject;
    private String content;
}
