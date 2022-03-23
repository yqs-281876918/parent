package org.mixed.exam.bank.pojo.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Exam
{
    @Id
    private String id;
    private String paperID;
    private Integer testTime;
    private Long startTime;
    private Integer lateTime;
    private Integer submitTime;
    private String classID;
    private String[] antiSettings;
}
