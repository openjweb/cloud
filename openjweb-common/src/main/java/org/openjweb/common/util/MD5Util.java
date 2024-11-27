package org.openjweb.common.util;


import java.security.MessageDigest;

public class MD5Util {
    //private static final String SALT = "";//不加盐的模式
    public static String encode(String password,String salt) {
        //password = password + SALT;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        String encodePassword =  hexValue.toString();
        //System.out.println("加密口令:");
        // '{MD5}2b784a1597b178cbc60261535aabef24'----如果用shiro的话，在shiro中需要把{MD5}去掉。
        // System.out.println(encodePassword);
        return encodePassword;
    }
}