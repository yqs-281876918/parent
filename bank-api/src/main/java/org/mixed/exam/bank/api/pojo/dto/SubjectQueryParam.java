package org.mixed.exam.bank.api.pojo.dto;

import lombok.Data;

@Data
public class SubjectQueryParam
{
    private String type=null;
    private Boolean open=null;
    private Boolean isExamined=null;
    private Integer difficulty=null;
    private String courseID=null;
    private String class2ndID=null;
    private String creator=null;
}
