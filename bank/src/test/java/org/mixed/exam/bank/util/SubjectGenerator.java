package org.mixed.exam.bank.util;

import lombok.Data;
import org.mixed.exam.bank.pojo.po.SingleChoiceQuestion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubjectGenerator {
    public static ChineseWrongCharacter CHINESE_WRONG_CHARACTER = new ChineseWrongCharacter();
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
        q.setAnswer(String.valueOf((int)(Math.random()*4)+1));
        return q;
    }
}