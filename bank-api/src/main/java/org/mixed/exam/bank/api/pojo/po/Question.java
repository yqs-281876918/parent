package org.mixed.exam.bank.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 题目的基类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question
{
    private String id;//题目主键id
    private Boolean isExamined=false;//是否审核通过
    private Boolean open=true;//题目是否开放
    private String type="null";//题目类型
    private Integer difficulty=-1;//题目难度
    private Date date=new Date();//题目时间
    private String courseID="-1";//题目所属的科目ID
    private String class2ndID="-1";//二级分类的ID
    private Integer recommendScore=-1;//建议分值
    private Integer respondentCount=0;//答题人数
    private Integer correctCount=0;//答对人数
    private String introduction="暂无简介";//题目简介，用于显示题目列表时使用
    private String creator;//创建人
    //获得正确率
    public double getCorrectRate()
    {
        if(respondentCount==null||correctCount==null)
        {
            throw new RuntimeException("答题人数或者正确人数为空");
        }
        return (double)correctCount/respondentCount;
    }
}