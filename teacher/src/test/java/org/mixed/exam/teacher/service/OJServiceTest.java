package org.mixed.exam.teacher.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OJServiceTest {

    @Autowired
    private OJService ojService;
    @Test
    void judge() {
        ojService.judge("import java.util.Scanner;\n" +
                "public class Main {\n" +
                "  public static void main(String[] args) {\n" +
                "\tScanner scanner=new Scanner(System.in);\n" +
                "\tint n = scanner.nextInt();\n" +
                "\tint ans=0;\n" +
                "\tfor(int i=0;i<n;i++)\n" +
                "\t{\n" +
                "\t\tans+=scanner.nextInt();\n" +
                "\t}\n" +
                "\tSystem.out.println(ans);\n" +
                "  }\n" +
                "}","62566da6d6429d288bc0c441","yqs");
    }
    @Test
    void testTrim(){
        String s = "\r\n\r ok\na b\n \t  ";
        System.out.println("begin");
        System.out.print(s.trim());
        System.out.println("end");
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n = scanner.nextInt();
        int ans=0;
        for(int i=0;i<n;i++)
        {
            ans+=scanner.nextInt();
        }
        System.out.println(ans);
    }
}