package org.mixed.exam.admin.service;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * 敏感词汇service
 */
@Service
public class SensitiveWordService {
    @Autowired
    public void init(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }
    public JdbcTemplate jdbcTemplate;
    @Autowired
    private SubjectClient subjectClient;

    private StringBuilder replaceAll;
    private String encoding = "UTF-8";
    /**替换字符窜**/
    private String replceStr = "*";
    /**单次替换的敏感词汇的长度*/
    private int replceSize = 500;
    /**敏感词汇文件*/
    //private String fileName = "SensitiveWord.txt";
    private List<String> arrayList;
    /**包含的敏感词列表，过滤掉重复项*/
    public Set<String> sensitiveWordSet;
    /**包含的敏感词列表，包括重复项，统计次数*/
    public List<String> sensitiveWordList;
    /**移除敏感词汇
     * @param str 需要过滤的字符窜
     * @return 过滤之后的字符窜
     */
//    public String removeSensitiveWord(String str){
//        SensitiveWordService sw = new SensitiveWordService("SensitiveWord.txt");
//        sw.InitializationWork();
//        return sw.filterInfo(str);
//    }
    /**拦截信息
     * <P>
     * 过滤掉敏感词汇的方法
     * @param str 将要被过滤信息
     * @return 过滤后的信息
     */
    public String filterInfo(String str) {
        sensitiveWordSet = new HashSet<String>();
        sensitiveWordList= new ArrayList<>();
        StringBuilder buffer = new StringBuilder(str);
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>(arrayList.size());
        String temp;
        for(int x = 0; x < arrayList.size();x++) {
            temp = arrayList.get(x);
            int findIndexSize = 0;
            for(int start = -1;(start=buffer.indexOf(temp,findIndexSize)) > -1;){
                //System.out.println("###replace="+temp);
                findIndexSize = start+temp.length();//从已找到的后面开始找
                Integer mapStart = hash.get(start);//起始位置
                //满足1个，即可更新map
                if(mapStart == null || (mapStart != null && findIndexSize > mapStart)){
                    hash.put(start, findIndexSize);
                    //System.out.println("###敏感词："+buffer.substring(start, findIndexSize));
                }
            }
        }
        Collection<Integer> values = hash.keySet();
        for(Integer startIndex : values){
            Integer endIndex = hash.get(startIndex);
            //获取敏感词，并加入列表，用来统计数量
            String sensitive = buffer.substring(startIndex, endIndex);
            //System.out.println("###敏感词："+sensitive);
            if (!sensitive.contains("*")) {//添加敏感词到集合
                sensitiveWordSet.add(sensitive);
                sensitiveWordList.add(sensitive);
            }
            buffer.replace(startIndex, endIndex, replaceAll.substring(0,endIndex-startIndex));
        }
        hash.clear();
        return buffer.toString();
    }
    /**初始化敏感词库*/
//    @Autowired
    private void InitializationWork() {
        replaceAll = new StringBuilder(replceSize);
        for(int x=0;x < replceSize;x++)
        {
            replaceAll.append(replceStr);
        }
        //加载词库
        arrayList = new ArrayList<String>();
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
//            read = new InputStreamReader(SensitiveWordService.class.getClassLoader().getResourceAsStream(fileName),encoding);
            String sql="SELECT word FROM verify";
            System.out.println(sql);
            List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
            System.out.println("list:"+list);
            String badword=null;
            for (Map<String, Object> m : list)
            {
                for (String k : m.keySet())
                {
                    System.out.println(k + " : " + m.get(k));
                    badword=badword+m.get(k)+"\n";
                }

            }
            InputStream is=new ByteArrayInputStream(badword.getBytes());
            read = new InputStreamReader(is);
            bufferedReader = new BufferedReader(read);
            for(String txt = null;(txt = bufferedReader.readLine()) != null;){
                if(!arrayList.contains(txt))
                    arrayList.add(txt);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(null != bufferedReader)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != read)
                    read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean wordCheck(String id,String type){
        System.out.println("t01");
        var str=subjectClient.getSubjectByID(id,type).toString();//waiting
        long startNumer = System.currentTimeMillis();
        //SensitiveWordService sw = new SensitiveWordService("verify");
        InitializationWork();
        //System.out.println("敏感词的数量：" + arrayList.size());
        System.out.println("被检测字符串长度:"+str.length());
        String strstart=str;
        str = filterInfo(str);
        long endNumber = System.currentTimeMillis();
        //System.out.println("语句中包含敏感词的个数为：" + sensitiveWordSet.size() + "。包含：" + sensitiveWordSet);
        //System.out.println("语句中包含敏感词的个数为：" + sensitiveWordList.size() + "。包含：" + sensitiveWordList);
        System.out.println("总共耗时:"+(endNumber-startNumer)+"ms");
        System.out.println("替换后的字符串为:\n"+str);
        System.out.println("替换后的字符串长度为:\n"+str.length());
        if(str.equals(strstart)){
            //System.out.println("type:"+);
            return true;
        }else{
            return false;
        }
    }
    public SensitiveWordService(){
    }
}
