package org.mixed.exam.bank.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mixed.exam.bank.pojo.po.Exam;

@Data
@NoArgsConstructor
public class ExamDto{
    private Integer id;
    private String paperID;
    private String examName;
    private Integer testTime;
    private Long startTime;
    private Integer lateTime;
    private Integer submitTime;
    private String classID;
    private String[] antiSettings;
    private String introduce;

}
