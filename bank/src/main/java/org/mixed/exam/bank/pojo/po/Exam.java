package org.mixed.exam.bank.pojo.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Exam
{
    @Id
    private Integer id;
    private String paperID;
    private String scoreList;//每道题目的分数列表
    private Integer totalScore;//试卷的总分
    private String examName;
    private Integer testTime;
    private Long startTime;
    private Integer lateTime;
    private Integer submitTime;
    private Integer classID;
    private String[] antiSettings;
    private String introduce;
    private Integer status;//考试状态
    private Integer studentScore;//学生分数
}
