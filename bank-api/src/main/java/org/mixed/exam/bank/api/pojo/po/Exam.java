package org.mixed.exam.bank.api.pojo.po;

import lombok.Data;

@Data
public class Exam
{
    private String id;
    private String paperID;
    private Integer testTime;
    private Long startTime;
    private Integer lateTime;
    private Integer submitTime;
    private String classID;
    private String[] antiSettings;
    private String introduce;
}
