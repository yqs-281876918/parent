package org.mixed.exam.teacher.service;

import com.netflix.discovery.converters.Auto;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.po.ProgramProblem;
import org.mixed.exam.bank.api.util.SubjectUtil;
import org.mixed.exam.teacher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OJService
{
    @Autowired
    private static SubjectClient subjectClient;
    public int judge(String code,String id,String userName) {
        //获取输入列表
        String json = subjectClient.getSubjectByID(id);
        ProgramProblem programProblem = StringUtil.json2Object(json,ProgramProblem.class);
        List<String> inputs=new ArrayList<>();
        for(String input : programProblem.getInputs()){
            inputs.add(input.trim());
        }
        //创建临时文件
        String path = "D:/code/"+userName+"/"+id;
        File file = new File(path);
        file.mkdirs();
        File codeFile=new File(path+"/Main.java");
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(codeFile));
            osw.write(code);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        try {
            //编译Main.java
            ProcessBuilder compileJava = new ProcessBuilder();
            compileJava.command("javac",path+"/Main.java");
            compileJava.start();
            //执行Main.class文件
            ProcessBuilder processBuilder = new ProcessBuilder();
            //通过command方法传入命令，参数有两种，一个是可变参数，另一种是集合，将命令符分开传入或者添加到集合中一起传入均可
            processBuilder.command("java","-cp",path,"Main");
            //将标准输入流和错误输入流合并，通过标准输入流读取信息，
            processBuilder.redirectErrorStream(true);
            //执行命令
            Process start = processBuilder.start();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(start.getOutputStream());
            for(String input : inputs){
                outputStreamWriter.write(input);
                outputStreamWriter.write("\n");
                outputStreamWriter.flush();
            }
            outputStreamWriter.close();
            //获取回显输入流，进行打印输出
            InputStream inputStream = start.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuilder ret = new StringBuilder();
            while ((len = inputStreamReader.read(c)) != -1){
                ret.append(new String(c, 0, len));
            }
            System.out.println(ret.toString().trim());
        }
        catch (IOException e){
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    public void createCodeFile(String code,String id,String userName){
    }
}
