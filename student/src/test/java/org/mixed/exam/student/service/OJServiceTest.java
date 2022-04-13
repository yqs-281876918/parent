package org.mixed.exam.student.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class OJServiceTest {
    @Test
    void test(){
        command("java","-version");
        command("javac","-version");
    }
    @Test
    void command(String...commands){
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File("C:\\Program Files\\Java\\jdk1.8.0_291\\bin"));
        pb.command(commands);
        pb.redirectErrorStream(true);
        try {
            Process start = pb.start();
            int len = -1;
            char[] c = new char[1024];
            StringBuilder ret = new StringBuilder();
            InputStream inputStream = start.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            while ((len = inputStreamReader.read(c)) != -1) {
                ret.append(new String(c, 0, len));
            }
            System.out.println(ret.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}