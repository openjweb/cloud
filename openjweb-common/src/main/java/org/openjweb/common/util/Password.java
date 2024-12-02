package org.openjweb.common.util;


import java.security.MessageDigest;

/**
 * 将口令明文进行MD5加密
 *
 * @author bzwang
 *
 */
public class Password {
    public static String MD5EncodePass(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    /**
     * 解决带字符集的问题
     * @param sourceString
     * @param encode
     * @return
     */
    public static String MD5EncodePassEncoding(String sourceString,String encode) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");//带字符集的加密怎么不好用了
            resultString = byte2hexString(md.digest(resultString.getBytes(encode)));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public static final String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            /*
             * 去掉原MD5加密验证后增加的逗号 原句:buf.append(Long.toHexString((int) bytes[i] &
             * 0xff)+",");
             */
            buf.append(Long.toHexString((int) bytes[i] & 0xff));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        try {
            String a = "123456你好";
            //1 不指定字符集的加密
            String b = Password.MD5EncodePass(a);
            System.out.println("b==" + b);
            //2 指定字符集的加密
            String c = Password.MD5EncodePassEncoding(a, "utf-8");
            System.out.println("c==" + c);

        } catch (Exception ex) {
        }

    }



}
