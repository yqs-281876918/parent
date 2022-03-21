package org.mixed.exam.teacher.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class InvitationCode {
    private static final List<String> baseList;

    private static final String INDEX = "4NQ5XMV7WRKC0ILEZO2J1PSDH8AU3FBTG6Y9";

    static {
        List<String> list = new ArrayList<>();
        list.add("XLG27OR1QC34FK58JAMPEH9YWU0SZNIDVB6T");
        list.add("W8DCRPJKYL0FE5QTIVZB6O3X1HUN92SGA47M");
        list.add("07YHDTEK4NVBQRUZPSGI93C81O2AWJ6FXML5");
        list.add("QWID3F97N1KY2SZXVUE4LORACB8TJ50G6HMP");
        list.add("AYZQ8GVJR7PHS501B4CONL26X3WUK9TEFDIM");
        list.add("SMCIQ4F3RWH56V8NA7TJOB1ZULD29KGY0PEX");
        list.add("KI835MZWALV7DGP2JO041XHCQYEB9UF6TNRS");
        list.add("4NQ5XMV7WRKC0ILEZO2J1PSDH8AU3FBTG6Y9");
        list.add("3VPDTRGM0NUAEH7SW6945OIFC82BYZLXJK1Q");
        list.add("DEH1MXNC6IULY3K9GPT4WQJVR08B5FSA27OZ");
        list.add("PZ6WUQDT2MEIHA9Y8RKBL34XGFVSJN7OC150");
        list.add("7KEDTSVQURXIBJ816Y29LOPCNF0G35WZMAH4");
        list.add("JHOF7BVZ0ET1DWGX4Q3APCLRMI895K2UY6SN");
        list.add("80UVJDY3IXA15TP69ZFEHC7QMLGBKSWN2RO4");
        list.add("4653QA9NLIZCSJKTEPV2DU7BOGXF18MYH0RW");
        list.add("SOGYRP3TWH8ZJBI6U4KN7X2DFA0QC5VEL1M9");
        list.add("1S4YA6KNTZIXEC25P03JGOQUMBWRHL987DVF");
        list.add("NJMXCUTGO7ZL016SWPR8YHEVABKFI423D5Q9");
        list.add("CT02U35L1RH84FPKMGWJVYNBIZ7DE6SXAOQ9");
        list.add("2U06HM7TWP1YIBX49SZFEAR8D5QGKO3JVNCL");
        list.add("ABERN67VO40DFPJYS9H5KW21Z8M3CILGXUTQ");
        list.add("QP43YJ2ZKA81F7TCSXH6V0BRGEMWLDNO9U5I");
        list.add("0R7QKXBW3VLPHE48U1Z9DJYNAFTOCIMS56G2");
        list.add("0C4XVG98NM71HBUDQYZLFRE65SIPKOT23WAJ");
        list.add("T7YRN3LHWZJGBI4S2FM8UQP0DK69VXCO5EA1");
        list.add("D2MR7059AGWTSJP8EKUHXLQ6BFZONIVC134Y");
        list.add("MPB3SQOEU9XLV2R1Y6ZTHGKD8AF50JI4N7CW");
        list.add("73X4LRF52JC0OETVHMKUNPDI1SQ8BZ6AWY9G");
        list.add("GF9ASJP2V4HE5LOTY0R1I3C8MUNXW7BKDQ6Z");
        list.add("J3BLZDY7VGNWSKX0CP4MU18FQ5EHROAI629T");
        list.add("LTMR0UFP28BJ1OSWXV5G3AN4EKCQYH76IZD9");
        list.add("MDL7E8TWYS2PI6A3ZXBVJH9OKGQ510RCU4NF");
        list.add("L2K3WMCBQ6GUDFYZ810XSP4O7RIH59AVNTJE");
        list.add("DLPBXJ0ROH8AFINQUSME1246VC3T9YZK5G7W");
        list.add("7CHVSGR1ZEJDMXPF9OY0KT35I6NBLAQ4W28U");
        list.add("X4V3G9SIET678NUK0FM1BZDYLR2OJC5QWAPH");
        baseList = Collections.unmodifiableList(list);

    }

    public static String genInviteCode(long id) {
        int key = (int) (id % 36);
        char[] cs = new char[6];
        cs[0] = INDEX.charAt(key);
        String select = baseList.get(key);
        long code = 100000000 + id * 13;
        for (int i = 1; i < 6; i++) {
            cs[i] = select.charAt((int) (code % 36));
            code = code / 36;
        }
        return new String(cs);
    }
}
