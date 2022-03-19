package org.mixed.exam.paper.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IntelligentParam
{
    private int courseID;//试卷所属学课
    private long begin_day;//开始时间戳
    private long end_day;//结束时间戳
    private int difficulty;//试卷整体难度
    private List<SubjectDistribution> types;//题型要求
    private List<String> keys;//知识点涵盖
    @Data
    public static class SubjectDistribution
    {
        private String type;
        private int count;
    }
}
