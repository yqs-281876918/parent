package org.mixed.exam.teacher.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class InvitationCode {
    private static final List<String> baseList;

    private static final String INDEX = "4NQ5XMV7WRKCILEZ2J1PSDH8AU3FBTG6Y9";

    static {
        List<String> list = new ArrayList<>();
        list.add("XLG27R1QC34FK58JAMPEH9YWUSZNIDVB6T");
        list.add("W8DCRPJKYLFE5QTIVZB63X1HUN92SGA47M");
        list.add("7YHDTEK4NVBQRUZPSGI93C812AWJ6FXML5");
        list.add("QWID3F97N1KY2SZXVUE4LRACB8TJ5G6HMP");
        list.add("AYZQ8GVJR7PHS51B4CNL26X3WUK9TEFDIM");
        list.add("SMCIQ4F3RWH56V8NA7TJB1ZULD29KGYPEX");
        list.add("KI835MZWALV7DGP2J41XHCQYEB9UF6TNRS");
        list.add("4NQ5XMV7WRKCILEZ2J1PSDH8AU3FBTG6Y9");
        list.add("3VPDTRGMNUAEH7SW6945IFC82BYZLXJK1Q");
        list.add("DEH1MXNC6IULY3K9GPT4WQJVR8B5FSA27Z");
        list.add("PZ6WUQDT2MEIHA9Y8RKBL34XGFVSJN7C15");
        list.add("7KEDTSVQURXIBJ816Y29LPCNFG35WZMAH4");
        list.add("JHF7BVZET1DWGX4Q3APCLRMI895K2UY6SN");
        list.add("8UVJDY3IXA15TP69ZFEHC7QMLGBKSWN2R4");
        list.add("4653QA9NLIZCSJKTEPV2DU7BGXF18MYHRW");
        list.add("SGYRP3TWH8ZJBI6U4KN7X2DFAQC5VEL1M9");
        list.add("1S4YA6KNTZIXEC25P3JGQUMBWRHL987DVF");
        list.add("NJMXCUTG7ZL16SWPR8YHEVABKFI423D5Q9");
        list.add("CT2U35L1RH84FPKMGWJVYNBIZ7DE6SXAQ9");
        list.add("2U6HM7TWP1YIBX49SZFEAR8D5QGK3JVNCL");
        list.add("ABERN67V4DFPJYS9H5KW21Z8M3CILGXUTQ");
        list.add("QP43YJ2ZKA81F7TCSXH6VBRGEMWLDN9U5I");
        list.add("R7QKXBW3VLPHE48U1Z9DJYNAFTCIMS56G2");
        list.add("C4XVG98NM71HBUDQYZLFRE65SIPKT23WAJ");
        list.add("T7YRN3LHWZJGBI4S2FM8UQPDK69VXC5EA1");
        list.add("D2MR759AGWTSJP8EKUHXLQ6BFZNIVC134Y");
        list.add("MPB3SQEU9XLV2R1Y6ZTHGKD8AF5JI4N7CW");
        list.add("73X4LRF52JCETVHMKUNPDI1SQ8BZ6AWY9G");
        list.add("GF9ASJP2V4HE5LTYR1I3C8MUNXW7BKDQ6Z");
        list.add("J3BLZDY7VGNWSKXCP4MU18FQ5EHRAI629T");
        list.add("LTMRUFP28BJ1SWXV5G3AN4EKCQYH76IZD9");
        list.add("MDL7E8TWYS2PI6A3ZXBVJH9KGQ51RCU4NF");
        list.add("L2K3WMCBQ6GUDFYZ81XSP47RIH59AVNTJE");
        list.add("DLPBXJRH8AFINQUSME1246VC3T9YZK5G7W");
        list.add("7CHVSGR1ZEJDMXPF9YKT35I6NBLAQ4W28U");
        list.add("X4V3G9SIET678NUKFM1BZDYLR2JC5QWAPH");
        baseList = Collections.unmodifiableList(list);

    }

    public static String getInviteCode(long id) {
        int key = (int) (id % 34);
        char[] cs = new char[6];
        cs[0] = INDEX.charAt(key);
        String select = baseList.get(key);
        long code = 100000000 + id * 13;
        for (int i = 1; i < 6; i++) {
            code = code / 34;
            cs[i] = select.charAt((int) (code % 34));
        }
        return new String(cs);
    }
}
