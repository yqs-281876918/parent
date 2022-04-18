package org.mixed.exam.teacher.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class InvitationCode {
    private static final List<String> baseList;

    private static final String INDEX = "4NQ5XMV7WRKCLEZ2JPSDH8AU3FBTG6Y9";

    static {
        List<String> list = new ArrayList<>();
        list.add("XLG27RQC34FK58JAMPEH9YWUSZNDVB6T");
        list.add("W8DCRPJKYLFE5QTVZB63XHUN92SGA47M");
        list.add("7YHDTEK4NVBQRUZPSG93C82AWJ6FXML5");
        list.add("QWD3F97NKY2SZXVUE4LRACB8TJ5G6HMP");
        list.add("AYZQ8GVJR7PHS5B4CNL26X3WUK9TEFDM");
        list.add("SMCQ4F3RWH56V8NA7TJBZULD29KGYPEX");
        list.add("K835MZWALV7DGP2J4XHCQYEB9UF6TNRS");
        list.add("4NQ5XMV7WRKCLEZ2JPSDH8AU3FBTG6Y9");
        list.add("3VPDTRGMNUAEH7SW6945FC82BYZLXJKQ");
        list.add("DEHMXNC6ULY3K9GPT4WQJVR8B5FSA27Z");
        list.add("PZ6WUQDT2MEHA9Y8RKBL34XGFVSJN7C5");
        list.add("7KEDTSVQURXBJ86Y29LPCNFG35WZMAH4");
        list.add("JHF7BVZETDWGX4Q3APCLRM895K2UY6SN");
        list.add("8UVJDY3XA5TP69ZFEHC7QMLGBKSWN2R4");
        list.add("4653QA9NLZCSJKTEPV2DU7BGXF8MYHRW");
        list.add("SGYRP3TWH8ZJB6U4KN7X2DFAQC5VELM9");
        list.add("S4YA6KNTZXEC25P3JGQUMBWRHL987DVF");
        list.add("NJMXCUTG7ZL6SWPR8YHEVABKF423D5Q9");
        list.add("CT2U35LRH84FPKMGWJVYNBZ7DE6SXAQ9");
        list.add("2U6HM7TWPYBX49SZFEAR8D5QGK3JVNCL");
        list.add("ABERN67V4DFPJYS9H5KW2Z8M3CLGXUTQ");
        list.add("QP43YJ2ZKA8F7TCSXH6VBRGEMWLDN9U5");
        list.add("R7QKXBW3VLPHE48UZ9DJYNAFTCMS56G2");
        list.add("C4XVG98NM7HBUDQYZLFRE65SPKT23WAJ");
        list.add("T7YRN3LHWZJGB4S2FM8UQPDK69VXC5EA");
        list.add("D2MR759AGWTSJP8EKUHXLQ6BFZNVC34Y");
        list.add("MPB3SQEU9XLV2RY6ZTHGKD8AF5J4N7CW");
        list.add("73X4LRF52JCETVHMKUNPDSQ8BZ6AWY9G");
        list.add("GF9ASJP2V4HE5LTYR3C8MUNXW7BKDQ6Z");
        list.add("J3BLZDY7VGNWSKXCP4MU8FQ5EHRA629T");
        list.add("LTMRUFP28BJSWXV5G3AN4EKCQYH76ZD9");
        list.add("MDL7E8TWYS2P6A3ZXBVJH9KGQ5RCU4NF");
        list.add("L2K3WMCBQ6GUDFYZ8XSP47RH59AVNTJE");
        list.add("DLPBXJRH8AFNQUSME246VC3T9YZK5G7W");
        list.add("7CHVSGRZEJDMXPF9YKT356NBLAQ4W28U");
        list.add("X4V3G9SET678NUKFMBZDYLR2JC5QWAPH");
        baseList = Collections.unmodifiableList(list);

    }

    public static String getInviteCode(long id) {
        int key = (int) (id % 32);
        char[] cs = new char[6];
        cs[0] = INDEX.charAt(key);
        int keyBase = (int)(id%36);
        String select = baseList.get(keyBase);
        long code = 100000000 + id * 13;
        for (int i = 1; i < 6; i++) {
            code = code / 32;
            cs[i] = select.charAt((int) (code % 32));
        }
        return new String(cs);
    }
}
