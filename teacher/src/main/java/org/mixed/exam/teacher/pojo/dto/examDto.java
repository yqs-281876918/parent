package org.mixed.exam.teacher.pojo.dto;

import org.mixed.exam.teacher.pojo.po.exam;

public class examDto extends exam {
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getPaperID() {
        return paperID;
    }

    @Override
    public void setPaperID(String paperID) {
        this.paperID = paperID;
    }

    @Override
    public String getExamName() {
        return examName;
    }

    @Override
    public void setExamName(String examName) {
        this.examName = examName;
    }

    @Override
    public Integer getTestTime() {
        return testTime;
    }

    @Override
    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    @Override
    public Long getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public Integer getLateTime() {
        return lateTime;
    }

    @Override
    public void setLateTime(Integer lateTime) {
        this.lateTime = lateTime;
    }

    @Override
    public Integer getSubmitTime() {
        return submitTime;
    }

    @Override
    public void setSubmitTime(Integer submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String getClassID() {
        return classID;
    }

    @Override
    public void setClassID(String classID) {
        this.classID = classID;
    }

    @Override
    public String[] getAntiSettings() {
        return antiSettings;
    }

    @Override
    public void setAntiSettings(String[] antiSettings) {
        this.antiSettings = antiSettings;
    }

    @Override
    public String getIntroduce() {
        return introduce;
    }

    @Override
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
