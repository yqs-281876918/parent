package org.mixed.exam.bank.api.pojo.vo;

import lombok.Data;
import org.mixed.exam.bank.api.pojo.po.Paper;

import java.util.Date;

@Data
public class PaperItem
{
    private String id;
    private String foreWord;//前言
    private Integer difficulty=-1;//试卷难度
    private Date date=new Date();//试卷创建时间
    private String creator;//创建人
    public PaperItem(){}
    public PaperItem(Paper paper)
    {
        this.id=paper.getId();
        this.foreWord=paper.getForeWord();
        this.difficulty=paper.getDifficulty();
        this.date=paper.getDate();
        this.creator=paper.getCreator();
    }
}
