package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.pojo.po.*;
import org.mixed.exam.bank.service.AddSubjectService;
import org.mixed.exam.classify.api.pojo.Classification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm')")
public class AddSubjectController
{
    @Autowired
    private AddSubjectService addSubjectService;

    //单选题
    @ResponseBody
    @PostMapping("addSubject/singleChoice")
    public String singleChoice(@RequestParam("options")List<String> options,
                               @RequestParam("description")String description,
                               @RequestParam("courseID")String courseID,
                               @RequestParam("class2ndID")String class2ndID,
                               @RequestParam("recommendScore")Integer recommendScore,
                               @RequestParam("difficulty")Integer difficulty,
                               @RequestParam("answer")String answer,
                               @RequestParam("date") String date)
    {
        SingleChoiceQuestion question = new SingleChoiceQuestion();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("singleChoiceQuestion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertSingleChoice(question);
        return "添加成功";
    }
    //多选题
    @ResponseBody
    @PostMapping("addSubject/multipleChoice")
    public String multipleChoice(@RequestParam("options")List<String> options,
                                 @RequestParam("description")String description,
                                 @RequestParam("courseID")String courseID,
                                 @RequestParam("class2ndID")String class2ndID,
                                 @RequestParam("recommendScore")Integer recommendScore,
                                 @RequestParam("difficulty")Integer difficulty,
                                 @RequestParam("answers")List<String> answers,
                                 @RequestParam("date") String date){
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswers(answers);
        question.setType("multipleChoiceQuestion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertMultipleChoice(question);
        return "添加成功";
    }
    //填空题
    @ResponseBody
    @PostMapping("addSubject/completion")
    public String completion(@RequestParam("description")String description,
                             @RequestParam("courseID")String courseID,
                             @RequestParam("class2ndID")String class2ndID,
                             @RequestParam("recommendScore")Integer recommendScore,
                             @RequestParam("difficulty")Integer difficulty,
                             @RequestParam("answers")List<String> answers,
                             @RequestParam("date") String date){
        Completion question = new Completion();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswers(answers);
        question.setType("completion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertCompletion(question);
        return "添加成功";
    }
    //判断题
    @ResponseBody
    @PostMapping("addSubject/judgment")
    public String judgment(@RequestParam("options")List<String> options,
                           @RequestParam("description")String description,
                           @RequestParam("courseID")String courseID,
                           @RequestParam("class2ndID")String class2ndID,
                           @RequestParam("recommendScore")Integer recommendScore,
                           @RequestParam("difficulty")Integer difficulty,
                           @RequestParam("answer")String answer,
                           @RequestParam("date") String date)
    {
        Judgment question = new Judgment();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("judgment");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertJudgment(question);
        return "添加成功";
    }
    //名词解析题
    @ResponseBody
    @PostMapping("addSubject/nounParsing")
    public String nounParsing(@RequestParam("description")String description,
                              @RequestParam("courseID")String courseID,
                              @RequestParam("class2ndID")String class2ndID,
                              @RequestParam("recommendScore")Integer recommendScore,
                              @RequestParam("difficulty")Integer difficulty,
                              @RequestParam("answer")String answer,
                              @RequestParam("date") String date)
    {
        NounParsing question = new NounParsing();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("nounParsing");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertNounParsing(question);
        return "添加成功";
    }
    //计算题
    @ResponseBody
    @PostMapping("addSubject/calculationProblem")
    public String calculationProblem(@RequestParam("description")String description,
                                     @RequestParam("courseID")String courseID,
                                     @RequestParam("class2ndID")String class2ndID,
                                     @RequestParam("recommendScore")Integer recommendScore,
                                     @RequestParam("difficulty")Integer difficulty,
                                     @RequestParam("answer")String answer,
                                     @RequestParam("date") String date)
    {
        CalculationProblem question = new CalculationProblem();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("calculationProblem");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertCalculationProblem(question);
        return "添加成功";
    }
    //分录题
    @ResponseBody
    @PostMapping("addSubject/entryProblem")
    public String entryProblem(@RequestParam("description")String description,
                               @RequestParam("courseID")String courseID,
                               @RequestParam("class2ndID")String class2ndID,
                               @RequestParam("recommendScore")Integer recommendScore,
                               @RequestParam("difficulty")Integer difficulty,
                               @RequestParam("answer")String answer,
                               @RequestParam("date") String date)
    {
        EntryProblem question = new EntryProblem();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("entryProblem");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertEntryProblem(question);
        return "添加成功";
    }
    //论述题
    @ResponseBody
    @PostMapping("addSubject/essayQuestion")
    public String essayQuestion(@RequestParam("description")String description,
                                @RequestParam("courseID")String courseID,
                                @RequestParam("class2ndID")String class2ndID,
                                @RequestParam("recommendScore")Integer recommendScore,
                                @RequestParam("difficulty")Integer difficulty,
                                @RequestParam("answer")String answer,
                                @RequestParam("date") String date)
    {
        EssayQuestion question = new EssayQuestion();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("essayQuestion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertEssayQuestion(question);
        return "添加成功";
    }
    //资料题
    @ResponseBody
    @PostMapping("addSubject/dataItems")
    public String dataItems(@RequestParam("description")String description,
                            @RequestParam("courseID")String courseID,
                            @RequestParam("class2ndID")String class2ndID,
                            @RequestParam("recommendScore")Integer recommendScore,
                            @RequestParam("difficulty")Integer difficulty,
                            @RequestParam("answer")String answer,
                            @RequestParam("date") String date)
    {
        DataItems question = new DataItems();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("dataItems");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertDataItems(question);
        return "添加成功";
    }
    //排序题
    @ResponseBody
    @PostMapping("addSubject/rankingQuestion")
    public String rankingQuestion(@RequestParam("options")List<String> options,
                                  @RequestParam("description")String description,
                                  @RequestParam("courseID")String courseID,
                                  @RequestParam("class2ndID")String class2ndID,
                                  @RequestParam("recommendScore")Integer recommendScore,
                                  @RequestParam("difficulty")Integer difficulty,
                                  @RequestParam("answer")String answer,
                                  @RequestParam("date") String date)
    {
        RankingQuestion question = new RankingQuestion();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("rankingQuestion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertRankingQuestion(question);
        return "添加成功";
    }
    //投票题
    @ResponseBody
    @PostMapping("addSubject/voteTopic")
    public String voteTopic(@RequestParam("options")List<String> options,
                            @RequestParam("description")String description,
                            @RequestParam("courseID")String courseID,
                            @RequestParam("class2ndID")String class2ndID,
                            @RequestParam("recommendScore")Integer recommendScore,
                            @RequestParam("difficulty")Integer difficulty,
                            @RequestParam("date") String date)
    {
        VoteTopic question = new VoteTopic();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setType("voteTopic");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertVoteTopic(question);
        return "添加成功";
    }
    //完型填空
    @ResponseBody
    @PostMapping("addSubject/clozeTest")
    public String clozeTest(@RequestParam("options")List<String> options,
                            @RequestParam("description")String description,
                            @RequestParam("courseID")String courseID,
                            @RequestParam("class2ndID")String class2ndID,
                            @RequestParam("recommendScore")Integer recommendScore,
                            @RequestParam("difficulty")Integer difficulty,
                            @RequestParam("answers")List<String> answers,
                            @RequestParam("date") String date){
        ClozeTest question = new ClozeTest();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswers(answers);
        question.setType("clozeTest");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertClozeTest(question);
        return "添加成功";
    }
    //阅读理解
    @ResponseBody
    @PostMapping("addSubject/readComprehension")
    public String readComprehension(@RequestParam("options")List<String> options,
                                    @RequestParam("description")String description,
                                    @RequestParam("courseID")String courseID,
                                    @RequestParam("class2ndID")String class2ndID,
                                    @RequestParam("recommendScore")Integer recommendScore,
                                    @RequestParam("difficulty")Integer difficulty,
                                    @RequestParam("answers")List<String> answers,
                                    @RequestParam("date") String date){
        ReadComprehension question = new ReadComprehension();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswers(answers);
        question.setType("readComprehension");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertReadComprehension(question);
        return "添加成功";
    }
    //听力题
    @ResponseBody
    @PostMapping("addSubject/listeningQuestion")
    public String listeningQuestion(@RequestParam("options")List<String> options,
                                    @RequestParam("description")String description,
                                    @RequestParam("courseID")String courseID,
                                    @RequestParam("class2ndID")String class2ndID,
                                    @RequestParam("recommendScore")Integer recommendScore,
                                    @RequestParam("difficulty")Integer difficulty,
                                    @RequestParam("answers")List<String> answers,
                                    @RequestParam("date") String date){
        ListeningQuestion question = new ListeningQuestion();
        question.setOptions(options);
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswers(answers);
        question.setType("listeningQuestion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertListeningQuestion(question);
        return "添加成功";
    }
    //综合题
    @ResponseBody
    @PostMapping("addSubject/comprehensiveQuestion")
    public String comprehensiveQuestion(@RequestParam("description")String description,
                                        @RequestParam("courseID")String courseID,
                                        @RequestParam("class2ndID")String class2ndID,
                                        @RequestParam("recommendScore")Integer recommendScore,
                                        @RequestParam("difficulty")Integer difficulty,
                                        @RequestParam("answer")String answer,
                                        @RequestParam("date") String date)
    {
        ComprehensiveQuestion question = new ComprehensiveQuestion();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("comprehensiveQuestion");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertComprehensiveQuestion(question);
        return "添加成功";
    }
    //口语题
    @ResponseBody
    @PostMapping("addSubject/oralTopic")
    public String oralTopic(@RequestParam("description")String description,
                            @RequestParam("courseID")String courseID,
                            @RequestParam("class2ndID")String class2ndID,
                            @RequestParam("recommendScore")Integer recommendScore,
                            @RequestParam("difficulty")Integer difficulty,
                            @RequestParam("date") String date)
    {
        OralTopic question = new OralTopic();
        question.setDescription(description);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setType("oralTopic");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertOralTopic(question);
        return "添加成功";
    }
    //程序题
    @ResponseBody
    @PostMapping("addSubject/programProblem")
    public String programProblem(@RequestParam("description")String description,
                                 @RequestParam("prepositionCode")String prepositionCode,
                                 @RequestParam("postCode")String postCode,
                                 @RequestParam("courseID")String courseID,
                                 @RequestParam("class2ndID")String class2ndID,
                                 @RequestParam("recommendScore")Integer recommendScore,
                                 @RequestParam("difficulty")Integer difficulty,
                                 @RequestParam("answer")String answer,
                                 @RequestParam("date") String date)
    {
        ProgramProblem question = new ProgramProblem();
        question.setDescription(description);
        question.setPrepositionCode(prepositionCode);
        question.setPostCode(postCode);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("programProblem");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertProgramProblem(question);
        return "添加成功";
    }
//    private List<String> optionsLeft = Collections.singletonList("null");
//    private List<String> optionsRight = Collections.singletonList("null");
//    private List<String> answer=Collections.singletonList("null");
    //连线题
    @ResponseBody
    @PostMapping("addSubject/matching")
    public String matching(@RequestParam("description")String description,
                           @RequestParam("optionsLeft")List<String> optionsLeft,
                           @RequestParam("optionsRight")List<String> optionsRight,
                           @RequestParam("courseID")String courseID,
                           @RequestParam("class2ndID")String class2ndID,
                           @RequestParam("recommendScore")Integer recommendScore,
                           @RequestParam("difficulty")Integer difficulty,
                           @RequestParam("answer")List<String> answer,
                           @RequestParam("date") String date)
    {
        Matching question = new Matching();
        question.setDescription(description);
        question.setOptionsLeft(optionsLeft);
        question.setOptionsRight(optionsRight);
        question.setCourseID(courseID);
        question.setClass2ndID(class2ndID);
        question.setRecommendScore(recommendScore);
        question.setDifficulty(difficulty);
        question.setAnswer(answer);
        question.setType("matching");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            question.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSubjectService.insertMatching(question);
        return "添加成功";
    }
    //查找所有课程信息
    @ResponseBody
    @GetMapping("classify/findAllCourse")
    public List<Classification> findAllCourse(){
        return addSubjectService.findAllCourse();
    }
    //查找所有课程知识点信息
    @ResponseBody
    @GetMapping("classify/findAllKnowledge")
    public List<Classification> findAllKnowledge(){
        return addSubjectService.findAllKnowledge();
    }
}
