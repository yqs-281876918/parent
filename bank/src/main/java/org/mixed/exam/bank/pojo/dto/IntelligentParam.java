package org.mixed.exam.bank.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class IntelligentParam
{
    private String courseID;//试卷所属学课
    private Date beginDay;//开始时间戳
    private Date endDay;//结束时间戳
    private double difficulty;//试卷整体难度[1~5]
    private Map<String,Integer> distribution;//题型要求
    private List<String> keys;//知识点涵盖
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public void setBeginDay(String date)
    {
        try {
            beginDay = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setEndDay(String date)
    {
        try {
            endDay = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public int getSubjectCount()
    {
        int count=0;
        for(String key : distribution.keySet())
        {
            count+=distribution.get(key);
        }
        return count;
    }
    @Data
    public static class SubjectDistribution
    {
        private String type;
        private int count;
        public SubjectDistribution()
        {
        }

        public SubjectDistribution(String type, int count) {
            this.type = type;
            this.count = count;
        }
    }
}
