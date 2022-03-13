package org.mixed.exam.verify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BanWordsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //private String filePath = "D:/zhx/softwear_engineering/java/newcode/new_exam/verify/src/main/resources/SensitiveWord.txt";
    public int addWord(String word){
        System.out.println(isItSame(word));
        if(isItSame(word).isEmpty()){
            //add
            String sql2="INSERT INTO verify (word) VALUES ('"+word+"')";
            if(jdbcTemplate.update(sql2)>0){
                return 1;
            }else {
                return 0;
            }
        }else {
            return 2;
        }

    }

    public List<Map<String, Object>> serchWord(String word){
        String sql = "SELECT * FROM verify WHERE word like '%"+word+"%'";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }

    public int deleteWord(String word){
        String sql = "DELETE FROM verify where word=?";
        //String sql = "DELETE FROM verify WHERE word = '"+word+"'";
        //jdbcTemplate.update(sql,"lucy");
        System.out.println(word);
        int rows=jdbcTemplate.update(sql,word);
        if(rows>0){
            return 1;
        }else {
            return 0;
        }
    }

    public int changeWord(String id,String word){
        System.out.println(isItSame(word));
        if(isItSame(word).isEmpty()){
            //add
            String sql = "update verify set word=? where id=?";
            int rows = jdbcTemplate.update(sql,word,id);
            if(rows>0){
                return 1;
            }else {
                return 0;
            }
        }else {
            return 2;
        }
    }


    public List<Map<String, Object>> isItSame(String word){
        String sql = "SELECT * FROM verify WHERE word = '"+word+"'";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }
//    public int addWord(String word){
//        //查询是否重复
//        List<String> tmp=new ArrayList();
//        List<String> check=serchWord(word);
//        if(check.size()==tmp.size()){
//            //不重复则存储并返回1
////            try {
////                File file = new File("D:/zhx/softwear_engineering/java/eaxm/code/exam/src/main/resources/SensitiveWord.txt");
////                PrintStream ps = new PrintStream(new FileOutputStream(file));
////                //ps.println(word);//写入字符串
////                ps.append(word);// 在已有的基础上添加字符串
////                return 1;
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////                return 0;
////            }
//            FileWriter fwriter = null;
//            try {
//                // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
//                fwriter = new FileWriter(filePath, true);
//                fwriter.write(word+"\n");
//                return 1;
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                return 0;
//            } finally {
//                try {
//                    fwriter.flush();
//                    fwriter.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }else{
//            //重复的话返回 0
//            System.out.println(check);
//            return 0;
//        }
//    }
//
//    public List<String> serchWord(String word){
//        //在txt中找这个词
//        List<String> error = new ArrayList();
//        error.add("error");
//        try {
//            String encoding="utf-8";
//            File file=new File(filePath);
//            System.out.println(file.isFile());
//            System.out.println(file.exists());
//            if(file.isFile() && file.exists()){ //判断文件是否存在
//                InputStreamReader read = new InputStreamReader(
//                        new FileInputStream(file),encoding);//编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                List<String> back=new ArrayList();
//                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
//                    //back=lineTxt;
//                    if(lineTxt.indexOf(word)!=-1){
//                        System.out.println("lineTxt.indexOf(word)"+" "+lineTxt.indexOf(word));
//                        back.add(lineTxt);
//                        System.out.println("lineTxt"+" "+lineTxt);
//                    }
//                }
//                read.close();
//                //return bufferedReader.readLine();
//                return back;
//            }else{
//                System.out.println("找不到指定的文件");
//                return error;
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//            return error;
//        }
//
//    }
//
//    public List<String> serchsameWord(String word){
//        //在txt中找这个词
//        List<String> error = new ArrayList();
//        error.add("error");
//        try {
//            String encoding="utf-8";
//            File file=new File(filePath);
//            System.out.println(file.isFile());
//            System.out.println(file.exists());
//            if(file.isFile() && file.exists()){ //判断文件是否存在
//                InputStreamReader read = new InputStreamReader(
//                        new FileInputStream(file),encoding);//编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                List<String> back=new ArrayList();
//                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
//                    //back=lineTxt;
//                    if(lineTxt.indexOf(word)!=-1){
//                        System.out.println("lineTxt.indexOf(word)"+" "+lineTxt.indexOf(word));
//                        back.add(lineTxt);
//                        System.out.println("lineTxt"+" "+lineTxt);
//                    }
//                }
//                read.close();
//                //return bufferedReader.readLine();
//                return back;
//            }else{
//                System.out.println("找不到指定的文件");
//                return error;
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//            return error;
//        }
//
//    }

//    public List<String> serchWord(String word){
//
//        List<String> back=jdbcTemplate.query(sql,new RowMapper<String>(){});
//    }
}
