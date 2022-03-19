package org.mixed.exam.paper.pojo.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
public class Paper
{
    @Id
    private String id;
    private String foreWord;//前言
    private List<List<String>> subjectIDs;//题目
    private String courseID;//试卷所属的学科
    private Boolean open=true;//试卷是否开放
    private Integer difficulty=-1;//试卷难度
    private Date date=new Date();//试卷创建时间
    private Integer respondentCount=0;//答题人数
    private Integer correctCount=0;//答对人数
    private String creator;//创建人
}