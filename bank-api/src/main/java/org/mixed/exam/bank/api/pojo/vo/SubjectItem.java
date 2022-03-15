package org.mixed.exam.bank.api.pojo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mixed.exam.bank.api.pojo.po.Question;

import java.util.Date;

@Data
@NoArgsConstructor
public class SubjectItem
{
    private String id;
    private String introduction;
    private String type;
    private Date date;
    private Integer difficulty;
    private String courseID="-1";//题目所属的科目ID
    private String class2ndID="-1";//二级分类的ID
    public SubjectItem(Question question)
    {
        this.id=question.getId();
        this.introduction=question.getIntroduction();
        this.type=question.getType();
        this.date=question.getDate();
        this.difficulty=question.getDifficulty();
        this.courseID = question.getCourseID();
        this.class2ndID = question.getClass2ndID();
    }
}
