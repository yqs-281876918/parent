package org.mixed.exam.bank.service;

import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.*;
import org.mixed.exam.classify.api.client.ClassifyClient;
import org.mixed.exam.classify.api.pojo.Classification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddSubjectService
{
    @Autowired
    SubjectDao subjectDao;
    @Autowired
    private ClassifyClient classifyClient;
    public void insertSingleChoice(SingleChoiceQuestion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertMultipleChoice(MultipleChoiceQuestion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertCompletion(Completion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertJudgment(Judgment question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertNounParsing(NounParsing question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertCalculationProblem(CalculationProblem question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertEntryProblem(EntryProblem question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertEssayQuestion(EssayQuestion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertDataItems(DataItems question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertRankingQuestion(RankingQuestion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertVoteTopic(VoteTopic question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertClozeTest(ClozeTest question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertReadComprehension(ReadComprehension question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertListeningQuestion(ListeningQuestion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertComprehensiveQuestion(ComprehensiveQuestion question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertOralTopic(OralTopic question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertProgramProblem(ProgramProblem question)
    {
        subjectDao.saveSubject(question);
    }
    public void insertMatching(Matching question)
    {
        subjectDao.saveSubject(question);
    }
    //课程相关
    //查找所有课程
    public List<Classification> findAllCourse(){
        return classifyClient.findAllCourse();
    }
    //查找所有课程相关知识点
    public List<Classification> findAllKnowledge(){
        return classifyClient.findAllKnowledge();
    }
}
