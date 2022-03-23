package org.mixed.exam.bank.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IntelligentParam
{
    private String courseID;//试卷所属学课
    private long begin_day;//开始时间戳
    private long end_day;//结束时间戳
    private double difficulty;//试卷整体难度[1~5]
    private List<SubjectDistribution> distributions;//题型要求
    private List<String> keys;//知识点涵盖
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public void setBeginDay(Date date)
    {
        begin_day=date.getTime();
    }
    public void setBeginDay(String date)
    {
        try {
            begin_day = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setEndDay(Date date)
    {
        end_day=date.getTime();
    }
    public void setEndDay(String date)
    {
        try {
            end_day = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public int getCount()
    {
        int count=0;
        for(SubjectDistribution sd : distributions)
        {
            count+=sd.getCount();
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
