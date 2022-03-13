package org.mixed.exam.verify.service;

import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.dto.SubjectJson;
import org.mixed.exam.bank.api.pojo.po.*;
import org.mixed.exam.bank.api.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mixed.exam.bank.api.util.SubjectUtil.json2Subject;

@Service
public class SimHashService {
//查重Service
    @Autowired
    private SubjectClient subjectClient;

    private String tokens;
    private BigInteger intSimHash;
    private String strSimHash;
    private int hashbits = 64;

    public SimHashService() throws IOException{

    }

    public SimHashService(String tokens) throws IOException {
        this.tokens = tokens;
        this.intSimHash = this.simHash();
    }

    public SimHashService(String tokens, int hashbits) throws IOException {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public BigInteger simHash() throws IOException {
        // 定义特征向量/数组
        int[] v = new int[this.hashbits];
        // 英文分词
//         StringTokenizer stringTokens = new StringTokenizer(this.tokens);
//         while (stringTokens.hasMoreTokens()) {
//         String temp = stringTokens.nextToken();
//         }
        // 中文分词，分词器采用 IKAnalyzer3.2.8
        StringReader reader = new StringReader(this.tokens);
        // 当为true时，分词器进行最大词长切分
        IKSegmentation ik = new IKSegmentation(reader, true);
        Lexeme lexeme = null;
        String word = null;
        String temp = null;
        while ((lexeme = ik.next()) != null) {
            word = lexeme.getLexemeText();
            // 注意停用词会被干掉
            System.out.println(word);
            // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(word);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    // 这里实际使用中需要 +- 权重，比如词频，而不是简单的 +1/-1，
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }

        BigInteger fingerprint = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            // 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            } else {
                simHashBuffer.append("0");
            }
        }
        this.strSimHash = simHashBuffer.toString();
        System.out.println(this.strSimHash + " length " + this.strSimHash.length());
        return fingerprint;
    }

    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    public int hammingDistance(SimHashService other) {

        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;

        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

    public int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public List subByDistance(SimHashService simHash, int distance) {
        // 分成几组来检查
        int numEach = this.hashbits / (distance + 1);
        List characters = new ArrayList();

        StringBuffer buffer = new StringBuffer();

        int k = 0;
        for (int i = 0; i < this.intSimHash.bitLength(); i++) {
            // 当且仅当设置了指定的位时，返回 true
            boolean sr = simHash.intSimHash.testBit(i);

            if (sr) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }

            if ((i + 1) % numEach == 0) {
                // 将二进制转为BigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                System.out.println("----" + eachValue);
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }
        }

        return characters;
    }

    public Question repeatCheck(String id, String type) throws IOException {
        Question back = null;
        //根据不通类型分类处理
        //单选题
        if (type.equals("singleChoiceQuestion")) {
            //所有单选题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            SingleChoiceQuestion q = (SingleChoiceQuestion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 =delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                SingleChoiceQuestion attribute=(SingleChoiceQuestion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //多选
        if (type.equals("multipleChoiceQuestion")) {
            //所有多选题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查多选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            MultipleChoiceQuestion q = (MultipleChoiceQuestion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                MultipleChoiceQuestion attribute=(MultipleChoiceQuestion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //填空
        if (type.equals("completion")) {
            //所有填空
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查填空
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            Completion q = (Completion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //答案部分
            String s3 = delHTMLTag(String.join("",q.getAnswers()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                Completion attribute=(Completion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getAnswers()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //判断
        if (type.equals("judgment")) {
            //所有判断题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            Judgment q = (Judgment) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            int smallest1 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                Judgment attribute=(Judgment)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                }

            }

        }
        //名词解析
        if(type.equals("nounParsing")){
            //所有名词解析
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            NounParsing q = (NounParsing) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //答案部分
            String s3 = delHTMLTag(q.getAnswer());
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                NounParsing attribute=(NounParsing)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(attribute.getAnswer());
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //论述
        if (type.equals("essayQuestion")) {
            //所有分录题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            EssayQuestion q = (EssayQuestion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getAnswer()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                EssayQuestion attribute=(EssayQuestion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getAnswer()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //计算
        if(type.equals("calculationProblem")){
            //所有计算
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            CalculationProblem q = (CalculationProblem) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //答案部分
//            String s3 = q.getAnswer();
//            System.out.println("s3" + s3);
//            SimHashService hash3 = new SimHashService(s3, 64);
//            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
//            // 计算 海明距离 在 3 以内的各块签名的 hash 值
//            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
//            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                CalculationProblem attribute=(CalculationProblem)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                    //判断答案是否重复
//                    String s4 = attribute.getAnswer();
//                    SimHashService hash4 = new SimHashService(s4, 64);
//                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
//                    hash3.subByDistance(hash4, 3);

//                    System.out.println("答案============================");
//                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
//                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
//                    //判断答案是否重复
//                    if(hash3.hammingDistance(hash4)<smallest2){
//                        smallest2=hash3.hammingDistance(hash4);
//                        back=attribute;
//                    }
                }

            }

        }
        //分录
        if (type.equals("entryProblem")) {
            //所有分录题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            EntryProblem q = (EntryProblem) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getAnswer()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                EntryProblem attribute=(EntryProblem)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getAnswer()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //资料
        if (type.equals("dataItems")) {
            //所有资料题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            DataItems q = (DataItems) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            int smallest1 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                DataItems attribute=(DataItems)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                }

            }

        }
        //连线
        if (type.equals("matching")) {
            //所有连线题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            Matching q = (Matching) SubjectUtil.json2Subject(json);
            //题目描述部分
            String t1 = String.join("",q.getOptionsLeft());
            String t2 = String.join("",q.getOptionsRight());
            String s1 = delHTMLTag(t1+t2);
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            int smallest1 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                Matching attribute=(Matching)json2Subject(att);

                String t3 = String.join("",attribute.getOptionsLeft());
                String t4 = String.join("",attribute.getOptionsRight());
                String s2 = delHTMLTag(t3+t4);
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                }

            }

        }
        //投票
        if (type.equals("voteTopic")) {
            //所有投票题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            VoteTopic q = (VoteTopic) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                VoteTopic attribute=(VoteTopic)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //排序
        if (type.equals("rankingQuestion")) {
            //所有排序题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            RankingQuestion q = (RankingQuestion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                RankingQuestion attribute=(RankingQuestion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //完形填空
        if (type.equals("clozeTest")) {
            //所有完形填空
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            ClozeTest q = (ClozeTest) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                ClozeTest attribute=(ClozeTest)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //阅读理解
        if (type.equals("readComprehension")) {
            //所有阅读理解
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            ReadComprehension q = (ReadComprehension) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                ReadComprehension attribute=(ReadComprehension)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //综合
        if (type.equals("comprehensiveQuestion")) {
            //所有综合题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            ComprehensiveQuestion q = (ComprehensiveQuestion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            int smallest1 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                ComprehensiveQuestion attribute=(ComprehensiveQuestion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                }

            }

        }
        //口语
        if (type.equals("oralTopic")) {
            //所有口语题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            OralTopic q = (OralTopic) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            int smallest1 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                OralTopic attribute=(OralTopic)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                }

            }

        }
        //听力
        if (type.equals("listeningQuestion")) {
            //所有听力
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            ListeningQuestion q = (ListeningQuestion) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            //选项部分
            String s3 = delHTMLTag(String.join("",q.getOptions()));
            System.out.println("s3" + s3);
            SimHashService hash3 = new SimHashService(s3, 64);
            System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash3.subByDistance(hash3, 3);

            int smallest1 = 4;
            int smallest2 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                ListeningQuestion attribute=(ListeningQuestion)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    //back=attribute;
                    //判断答案是否重复
                    String s4 = delHTMLTag(String.join("",attribute.getOptions()));
                    SimHashService hash4 = new SimHashService(s4, 64);
                    System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
                    hash3.subByDistance(hash4, 3);

                    System.out.println("答案============================");
                    int dis2 = hash3.getDistance(hash3.strSimHash, hash4.strSimHash);
                    System.out.println(hash3.hammingDistance(hash4) + " " + dis2);
                    //判断答案是否重复
                    if(hash3.hammingDistance(hash4)<smallest2){
                        smallest2=hash3.hammingDistance(hash4);
                        back=attribute;
                    }
                }

            }

        }
        //程序
        if (type.equals("programProblem")) {
            //所有程序题
            List<SubjectJson> question = subjectClient.subjects(type);
            //所查单选题
            SubjectJson json = subjectClient.getSubjectByID(id, type);
            ProgramProblem q = (ProgramProblem) SubjectUtil.json2Subject(json);
            //题目描述部分
            String s1 = delHTMLTag(q.getDescription());
            SimHashService hash1 = new SimHashService(s1, 64);
            System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
            // 计算 海明距离 在 3 以内的各块签名的 hash 值
            hash1.subByDistance(hash1, 3);

            int smallest1 = 4;
            //遍历判断是否重复
            for (SubjectJson att : question) {
                //String id2=attribute.getid();
                //System.out.println(attribute);
                ProgramProblem attribute=(ProgramProblem)json2Subject(att);

                String s2 = delHTMLTag(attribute.getDescription());
                SimHashService hash2 = new SimHashService(s2, 64);
                System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
                hash1.subByDistance(hash2, 3);

                System.out.println("题目============================");
                int dis1 = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
                System.out.println(hash1.hammingDistance(hash2) + " " + dis1);
                //判断为题目描述重复
                if (hash1.hammingDistance(hash2) < smallest1 && q.getId().equals(attribute.getId())!=true) {
                    smallest1 = hash1.hammingDistance(hash2);
                    back=attribute;
                }

            }

        }
        System.out.println(back);
        return back;
    }

    //去掉富文本标签
    private static String delHTMLTag(String htmlStr) {
        if (htmlStr == null || "".equals(htmlStr)) {
            return "";
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style的正则表达式
        String regEx_html = "<[^>]+>"; // HTML标签的正则表达式
        // 过滤script标签
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        return htmlStr;
    }

}