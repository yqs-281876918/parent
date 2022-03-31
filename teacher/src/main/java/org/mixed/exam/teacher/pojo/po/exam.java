package org.mixed.exam.teacher.pojo.po;

import lombok.Data;

@Data
public class exam {
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
