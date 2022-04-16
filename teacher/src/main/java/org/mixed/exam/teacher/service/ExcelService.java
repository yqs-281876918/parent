package org.mixed.exam.teacher.service;

import jxl.*;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.client.ExamDetailClient;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.Paper;
import org.mixed.exam.bank.api.pojo.po.Question;
import org.mixed.exam.bank.api.pojo.po.exam.Answer;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.teacher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService
{
    public byte[] listToExcel(List<ExamDetail> items){
        try {
            //FileOutputStream fos = new FileOutputStream(new File("D:/1.xls"));
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            //创建可写入的Excel工作薄，且内容将写入到输出流，并通过输出流输出给客户端浏览
            WritableWorkbook wk = Workbook.createWorkbook(bao);
            //创建可写入的Excel工作表
            WritableSheet sheet = wk.createSheet("成绩表", 0);
            //写标题
            int scoreCount = 0;
            if(items!=null&&items.size()!=0){
                scoreCount=items.get(0).getAnswers().size();
            }
            Label lab_0 = new Label(0, 0, "排名", getTitleCellFormat());
            Label lab_1 = new Label(1, 0, "姓名", getTitleCellFormat());
            sheet.addCell(lab_0);
            sheet.addCell(lab_1);
            for(int i=0;i<scoreCount;i++){
                Label lab_sub=new Label(2+i,0,"分值"+(i+1),getTitleCellFormat());
                sheet.addCell(lab_sub);
            }
            Label lab_2 = new Label(2+scoreCount, 0, "总分", getTitleCellFormat());
            sheet.addCell(lab_2);
            //写内容
            int length=items.size();
            int rank=1;
            for(int i=0;i<length;i++){
                ExamDetail item = items.get(i);
                Label lab_rank=new Label(0,i+1,String.valueOf(rank++),getContentCellFormat());
                Label lab_name=new Label(1,i+1,item.getUsername(),getContentCellFormat());
                for(int j=0;j<scoreCount;j++){
                    Label lab_sub=new Label(2+j,i+1,""+item.getAnswers().get(j).getScore(),
                            getTitleCellFormat());
                    sheet.addCell(lab_sub);
                }
                Label lab_total=new Label(2+scoreCount,i+1,String.valueOf(item.getTotalScore()),
                        getContentCellFormat());
                sheet.addCell(lab_rank);
                sheet.addCell(lab_name);
                sheet.addCell(lab_total);
            }
            wk.write();
            bao.flush();
            wk.close();
            bao.close();
            return bao.toByteArray();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    private static WritableCellFormat contentCellFormat=null;
    private WritableCellFormat getContentCellFormat(){
        if(contentCellFormat!=null){
            return contentCellFormat;
        }
        return new WritableCellFormat();
    }
    private static WritableCellFormat titleCellFormat=null;
    private WritableCellFormat getTitleCellFormat() {
        if(titleCellFormat!=null){
            return titleCellFormat;
        }
        try {
            WritableFont titleFont = new WritableFont(WritableFont.createFont("黑体"), 12,
                    WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
            titleCellFormat=new WritableCellFormat();
            //设置字体格式
            titleCellFormat.setFont(titleFont);
            //设置文本水平居中对齐
            titleCellFormat.setAlignment(Alignment.CENTRE);
            //设置文本垂直居中对齐
            titleCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            //设置自动换行
            //titleFormat.setWrap(true);
            return titleCellFormat;
        }catch (WriteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Autowired
    private ExamClient examClient;
    private PaperClient paperClient;
    private SubjectClient subjectClient;
    private ExamDetailClient examDetailClient;
    public void importExcel(InputStream fis,Integer examID){
        Exam exam = examClient.getByID(examID);
        Paper paper = paperClient.getByID(exam.getPaperID());
        List<String> subjectIDs = paper.getSubjectIDs();
        List<Question> questions = new ArrayList<>();
        for(String id : subjectIDs){
            String json = subjectClient.getSubjectByID(id);
            Question question = StringUtil.json2Object(json, Question.class);
            questions.add(question);
        }
        int scoreCount=questions.size();
        try {
            Workbook wk = Workbook.getWorkbook(fis);
            //获取第一张Sheet表
            Sheet sheet = wk.getSheet(0);
            //获取总行数
            int rowNum = sheet.getRows();
            //从数据行开始迭代每一行
            for (int i = 1; i < rowNum; i++) {
                ExamDetail examDetail = new ExamDetail();
                examDetail.setExamId(examID);
                examDetail.setFinishReview(1);
                examDetail.setAntiCount(0);
                examDetail.setAnswers(new ArrayList<>());
                //getCell(column,row)，表示取得指定列指定行的单元格（Cell）
                //getContents()获取单元格的内容，返回字符串数据。适用于字符型数据的单元格
                //第i行第一列
                String name = sheet.getCell(1, i).getContents();
                examDetail.setUsername(name);
                int total=0;
                for(int j=2;j<2+scoreCount;j++){
                    Answer answer = new Answer();
                    answer.setAnswerList(null);
                    answer.setSubjectId(questions.get(i).getId());
                    answer.setSubjectType(questions.get(i).getType());
                    if(sheet.getCell(j,i).getType() != CellType.NUMBER){
                        answer.setScore(0);
                    }else {
                        int score=Integer.parseInt(sheet.getCell(j,i).getContents());
                        total+=score;
                        answer.setScore(score);
                    }
                    examDetail.getAnswers().add(answer);
                }
                examDetail.setTotalScore(total);
                examDetailClient.addExamDetail(examDetail);
            }
            //关闭流
            fis.close();
            wk.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }
}
