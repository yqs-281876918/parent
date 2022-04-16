package org.mixed.exam.student.service;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.ProgramProblem;
import org.mixed.exam.student.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class OJService
{
    @Autowired
    private SubjectClient subjectClient;
    public int judge(String code,String id,String userName) {
        //获取输入列表
        String json = subjectClient.getSubjectByID(id);
        ProgramProblem programProblem = StringUtil.json2Object(json,ProgramProblem.class);
        if(programProblem==null)
        {
            return -1;
        }
        //创建临时文件
        String path = "D:/code/"+userName+"/"+id;
        File file = new File(path);
        file.mkdirs();
        //删除class文件
        File classFile=new File(path+"/Main.class");
        if(classFile.exists()){
            classFile.delete();
        }
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
        int correct=0;
        try {
            //编译Main.java
            ProcessBuilder compileJava = new ProcessBuilder();
            compileJava.command("javac", path + "/Main.java");
            Process compile_app = compileJava.start();
            compile_app.waitFor();
            if(!classFile.exists()){
                return -1;
            }
            //判断测试样例
            int length=programProblem.getInputs().size();
            for(int i=0;i<length;i++) {
                //执行Main.class文件
                ProcessBuilder processBuilder = new ProcessBuilder();
                //通过command方法传入命令，参数有两种，一个是可变参数，另一种是集合，将命令符分开传入或者添加到集合中一起传入均可
                processBuilder.command("java", "-cp", path, "Main");
                //将标准输入流和错误输入流合并，通过标准输入流读取信息，
                processBuilder.redirectErrorStream(true);
                //执行命令
                Process start = processBuilder.start();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(start.getOutputStream());
                outputStreamWriter.write(programProblem.getInputs().get(i));
                outputStreamWriter.flush();
                outputStreamWriter.close();
                //获取回显输入流，进行打印输出
                InputStream inputStream = start.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
                int len = -1;
                char[] c = new char[1024];
                StringBuilder ret = new StringBuilder();
                while ((len = inputStreamReader.read(c)) != -1) {
                    ret.append(new String(c, 0, len));
                }
                String output=ret.toString().trim();
                if(output.equals(programProblem.getOutputs().get(i).trim()))
                {
                    correct++;
                }
            }
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
            return -1;
        }
        return correct;
    }
    public void createCodeFile(String code,String id,String userName){
    }
}
