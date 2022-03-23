package org.mixed.exam.bank.util;

import lombok.Data;
import org.mixed.exam.bank.pojo.po.Completion;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.po.ReadComprehension;
import org.mixed.exam.bank.pojo.po.SingleChoiceQuestion;

import javax.management.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubjectGenerator {
    public static ChineseWrongCharacter CHINESE_WRONG_CHARACTER = new ChineseWrongCharacter();
    public static ChineseReadComprehension CHINESE_READ_COMPREHENSION=new ChineseReadComprehension();
    public static AncientWrite ANCIENT_WRITE=new AncientWrite();
    public static ChineseWriting CHINESE_WRITING=new ChineseWriting();
}
@Data
class ChineseWrongCharacter
{
    private String[][] ids = new String[][]{
            {"622dbb040913ed05f6402c8b","622dcb956fdcc85405ef85ad"}//语文 错词辨析
    };
    private String[] creators=new String[]{"yqs","qlh","yk","zyn","zhx"};
    public SingleChoiceQuestion randomSCQ()
    {
        SingleChoiceQuestion q = new SingleChoiceQuestion();
        q.setType("singleChoiceQuestion");
        q.setDifficulty((int)(Math.random()*5)+1);
        Date date_2000=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date_2000 = sdf.parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Date date_now=new Date();
        long date_between=date_now.getTime()-date_2000.getTime();
        Date date_random=new Date();
        date_random.setTime(date_2000.getTime()+(long) (Math.random()*date_between));
        q.setDate(date_random);
        q.setCourseID(ids[0][0]);//语文
        q.setClass2ndID(ids[0][1]);//错词辨析
        q.setRespondentCount((int)(Math.random()*100));
        q.setCorrectCount((int)(Math.random()*q.getRespondentCount()));
        q.setIntroduction("这是语文错词题目"+System.currentTimeMillis());
        q.setCreator(creators[(int)(Math.random()*5)]);
        q.setDescription("这是语文错词题目"+System.currentTimeMillis());
        q.setOptions(Arrays.asList("选项1","选项2","选项3","选项4"));
        q.setAnswer(String.valueOf((int)(Math.random()*4)+1));
        return q;
    }
}
class ChineseReadComprehension
{
    private String[] creators=new String[]{"yqs","qlh","yk","zyn","zhx"};

    public ReadComprehension randomRC()
    {
        ReadComprehension q = new ReadComprehension();
        q.setType("readComprehension");
        q.setDifficulty((int)(Math.random()*5)+1);
        Date date_2000=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date_2000 = sdf.parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Date date_now=new Date();
        long date_between=date_now.getTime()-date_2000.getTime();
        Date date_random=new Date();
        date_random.setTime(date_2000.getTime()+(long) (Math.random()*date_between));
        q.setDate(date_random);
        q.setCourseID("622dbb040913ed05f6402c8b");//语文
        q.setClass2ndID("62304bf274a387398923fe3b");//错词辨析
        q.setRespondentCount((int)(Math.random()*100));
        q.setCorrectCount((int)(Math.random()*q.getRespondentCount()));
        q.setIntroduction("这是语文阅读理解题目"+System.currentTimeMillis());
        q.setCreator(creators[(int)(Math.random()*5)]);
        q.setDescription("这是阅读理解题目"+System.currentTimeMillis());
        q.setOptions(Arrays.asList("第一题","第二题","第三题","第四题"));
        q.setAnswers(Arrays.asList("第一题答案","第二题答案","第三题答案","第四题答案"));
        return q;
    }
}
class AncientWrite
{
    private String[] creators=new String[]{"yqs","qlh","yk","zyn","zhx"};
    public Completion randomCompletion()
    {
        Completion q = new Completion();
        q.setType("completion");
        q.setDifficulty((int)(Math.random()*5)+1);
        Date date_2000=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date_2000 = sdf.parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Date date_now=new Date();
        long date_between=date_now.getTime()-date_2000.getTime();
        Date date_random=new Date();
        date_random.setTime(date_2000.getTime()+(long) (Math.random()*date_between));
        q.setDate(date_random);
        q.setCourseID("622dbb040913ed05f6402c8b");//语文
        q.setClass2ndID("6230776f1aabf7331f816aae");//古诗默写
        q.setRespondentCount((int)(Math.random()*100));
        q.setCorrectCount((int)(Math.random()*q.getRespondentCount()));
        q.setIntroduction("这是古诗默写题"+System.currentTimeMillis());
        q.setDescription(" 峰峦如聚，波涛如怒，___________");
        q.setAnswers(Arrays.asList("山河表里潼关"));
        return q;
    }
}
class ChineseWriting
{
    private String[] creators=new String[]{"yqs","qlh","yk","zyn","zhx"};
    public Completion randomCompletion()
    {
        Completion q = new Completion();
        q.setType("completion");
        q.setDifficulty((int)(Math.random()*5)+1);
        Date date_2000=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date_2000 = sdf.parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Date date_now=new Date();
        long date_between=date_now.getTime()-date_2000.getTime();
        Date date_random=new Date();
        date_random.setTime(date_2000.getTime()+(long) (Math.random()*date_between));
        q.setDate(date_random);
        q.setCourseID("622dbb040913ed05f6402c8b");//语文
        q.setClass2ndID("623074271aabf7331f816aad");//作文
        q.setRespondentCount((int)(Math.random()*100));
        q.setCorrectCount((int)(Math.random()*q.getRespondentCount()));
        q.setIntroduction("这是语文作文题"+System.currentTimeMillis());
        q.setDescription("请描述自己的老师");
        q.setAnswers(Arrays.asList("无"));
        return q;
    }
}
class AncientAppreciation
{
    private String[] creators=new String[]{"yqs","qlh","yk","zyn","zhx"};

}
//private String id;//题目主键id
//private Boolean isExamined=false;//是否审核通过
//private Boolean open=true;//题目是否开放
//private String type="null";//题目类型
//private Integer difficulty=-1;//题目难度
//private Date date=new Date();//题目时间
//private String courseID="-1";//题目所属的科目ID
//private String class2ndID="-1";//二级分类的ID
//private Integer recommendScore=-1;//建议分值
//private Integer respondentCount=0;//答题人数
//private Integer correctCount=0;//答对人数
//private String introduction="暂无简介";//题目简介，用于显示题目列表时使用
//private String creator;//创建人