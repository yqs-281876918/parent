package org.mixed.exam.paper.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IntelligentParam
{
    private int courseID;//试卷所属学课
    private int begin_day;
    private int end_day;//题目创建距今的时间区间[begin_day,end_day]
    private int difficulty;//试卷整体难度
    private List<String> types;//题型要求
    private int subjectCount;//题目数量
}
