package org.openjweb.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.openjweb.common.util.gbconvert.GB2Big5;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import org.apache.log4j.Logger;



/**
 * 字符串公用方法库,不要修改方法中的实现(在正式被使用之前可以更改)
 *
 * @author bzwang
 */
public class StringUtil {
    private static final Logger logger = Logger.getLogger(StringUtil.class);
    private static long currSerial = 0;

    /**
     * 获得一个32位长度的唯一序列号
     *
     * @return String 返回唯一序列号
     */

    public static final String getUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 遍历字符串，将字符串中的子串替换为新的子串
     *
     * @param strSource 字符串
     * @param strFrom   被替换的子串
     * @param strTo     用此串替换
     * @return String 返回一个替换后的字符串
     */
    public static final java.lang.String replace(java.lang.String strSource,
                                                 java.lang.String strFrom, java.lang.String strTo) {
        if (strSource == null) {
            return null;
        }
        java.lang.String strDest = "";
        int intFromLen = strFrom.length();
        int intPos;

        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            strDest = strDest + strSource.substring(0, intPos);
            strDest = strDest + strTo;
            strSource = strSource.substring(intPos + intFromLen);
        }
        strDest = strDest + strSource;

        return strDest;
    }

    /**
     * 按汉字的拼音排序
     *
     * @param ch1 被比较的字符串
     * @param ch2 比较的字符串
     * @return int 字母表中前面的字母比后面的字母数字小，返回正数表示字母靠后，如"中国"(Z)和"北京"比较，则返回正数
     */
    public static int compareCH(String ch1, String ch2) {
        try {
            // 取得汉字编码并将其转换成字符串
            String str1 = new String(ch1.getBytes("GB2312"), "ISO-8859-1");
            String str2 = new String(ch2.getBytes("GB2312"), "ISO-8859-1");

            return str1.compareTo(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 判断字符串是否为空(包括空值和零长度的非空字符串)
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 按字节截取字符串，用于页面中一行文字超长时，截掉末尾的文字,并在末尾加上... 如:"中国人民"，如显示5位字符串长度，则显示为"中国人..."
     *
     * @param len 显示长度
     * @param str 显示的字符串
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String substring(int len, String str)
            throws UnsupportedEncodingException {
        if (isEmpty(str) || len < 1)
            return "";

        if (str.getBytes().length < len)
            return str;

        char[] strchars = str.toCharArray();
        String substr = "";
        int totalLength = 0;
        for (int i = 0; i < strchars.length; i++) {
            byte[] strbytes = (new Character(strchars[i])).toString().getBytes(
                    "GBK");

            substr += strchars[i];

            totalLength += strbytes.length;
            if (totalLength > len)
                break;
        }

        return substr + "...";

    }


    public static String substringFrom(int len, String str, int startPos)
            throws UnsupportedEncodingException {
        if (str.length() < startPos + 1) return "";//如果起始位置超过字符串长度，则返回""
        if (isEmpty(str) || len < 1)
            return "";

        if (str.getBytes().length < len)
            return str;

        char[] strchars = str.substring(startPos).toCharArray();
        String substr = "";
        int totalLength = 0;
        for (int i = 0; i < strchars.length; i++) {
            byte[] strbytes = (new Character(strchars[i])).toString().getBytes("GBK");
            substr += strchars[i];
            totalLength += strbytes.length;
            if (totalLength > len)
                break;
        }

        if (!substr.equals(str))    //改为截取增加...
        {
            substr += "...";
        }
        return substr;// + "....";//不用省略号


    }

    /**
     * 将yyyymmdd格式日期转换为long
     *
     * @param sDate
     * @return
     */
    public static long getLongValueOfDate(String sDate) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(sDate);
        } catch (Exception ex) {
            logger.error("日期转换错误");
        }
        long lDate = date.getTime();
        return lDate;//转换为长整型日期


    }

    /**
     * 主要用于测试过程中输出调试时间(按日期格式显示)
     *
     * @param flag 自己设置的提示信息标签。
     */
    public static void printCurrentTime(String flag) {
        Calendar cls = Calendar.getInstance();
        cls.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        logger.debug(flag + "====时间：" + sdf.format(cls.getTime()) + "====");
    }

    /**
     * 将long型的时间转化为日期时分秒毫秒格式存储于数据库
     *
     * @param currTime
     * @return String 转化为数字字符串后的时间
     */

    public static String covertDBTime(long currTime) {
        String sDBTime = "";
        try {
            Calendar cls = Calendar.getInstance();
            cls.setTimeInMillis(currTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

            sDBTime = sdf.format(cls.getTime());
        } catch (Exception e) {
            logger.error("covertDBTime方法转换日期错误!");
            e.printStackTrace();
            sDBTime = "";
        }
        return sDBTime;
    }

    public static String convertURLString(Object s) {
        if (s == null) {
            return "";

        }

        String sReturnValue = (String) s;
        return sReturnValue;

    }

    public static String ArrayToString(String array[], String s) {
        StringBuffer sb = new StringBuffer();

        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (s != null)
                    sb.append(s);
            }
            if (s != null && !s.equals(""))
                sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    public static String[] encoding(String[] strArray, String encode) {

        if (strArray == null)
            return null;

        try {

            for (int i = 0; i < strArray.length; i++) {

                strArray[i] = new String(strArray[i].getBytes(encode));

            }

        } catch (Exception e) {

        }

        return strArray;

    }

    /**
     * 获得一个当前日期时分秒格式的字符串
     *
     * @return
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = dateFormat.format(new Date(System.currentTimeMillis()));
        return currDt;
    }

    /**
     * 返回日期时分秒毫秒格式字符串
     *
     * @return
     */
    public static String getCurrentDateTime2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = dateFormat.format(new Date(System.currentTimeMillis()));
        return currDt;
    }

    public static String getCurrentYmd() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = dateFormat.format(new Date(System.currentTimeMillis()));
        return currDt.substring(0, 4) + currDt.substring(5, 7)
                + currDt.substring(8, 10);
    }


    public static String parseLongDate(long dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = dateFormat.format(new Date(dt));
        return currDt;
    }

    /**
     * 返回yyyy-mm-dd格式
     *
     * @return
     */
    public static String getCurrentYmd2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = dateFormat.format(new Date(System.currentTimeMillis()));
        return currDt.substring(0, 10);

    }

    /**
     * 根据当前月份获得下个月的月份,月份格式为YYYYMM
     *
     * @return 返回指定月份的下一个月, 暂未实现算法
     */
    public static String getNextMonth() {

        java.util.Date d = new java.util.Date();
        java.text.SimpleDateFormat dformat1 = new java.text.SimpleDateFormat(
                "MM");
        java.text.SimpleDateFormat dformat2 = new java.text.SimpleDateFormat(
                "yyyy");
        dformat2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        dformat1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String syear = dformat2.format(d);
        String smonth = dformat1.format(d);
        if (smonth.equals("12")) {
            syear = String.valueOf(Integer.parseInt(syear) + 1);
            smonth = "01";
        } else {
            int currMonth = Integer.parseInt(smonth);
            if (currMonth >= 9) {
                smonth = String.valueOf(currMonth + 1);
            } else {
                smonth = "0" + String.valueOf(currMonth + 1);
            }
        }
        return syear + smonth;

    }

    /**
     * 根据一个表名获取对应的类名
     *
     * @param sTableName 表名
     * @return 实体类的名字(不带package路径)
     */
    public static String getEntityNameByTableName(String sTableName) {
        sTableName = sTableName.toLowerCase();// 首先全部转换为小写
        String[] tmpArray = sTableName.split("_");
        String sReturn = "";
        for (int i = 0; i < tmpArray.length; i++) {
            String sTemp = tmpArray[i].substring(0, 1).toUpperCase()
                    + tmpArray[i].substring(1);
            sReturn = sReturn + sTemp;
        }

        return sReturn;
    }

    public static String getTableNameByClsName(String clsName) {
        String sTemp = "";
        String tableName = "";
        if (clsName.indexOf(".") == -1) {
            sTemp = clsName;
        } else {
            sTemp = clsName.substring(clsName.lastIndexOf(".") + 1);

        }
        char[] chars = sTemp.toCharArray();//转字符数组
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                tableName += "_" + String.valueOf(chars[i]).toLowerCase();
            } else {
                tableName += String.valueOf(chars[i]);
            }
        }


        return tableName;
    }

    /**
     * 根据字段名称返回类属性名
     *
     * @param colName
     * @return
     */
    public static String getFieldNameByColName(String colName) {
        String sFieldName = "";
        sFieldName = StringUtil.getEntityNameByTableName(colName);
        sFieldName = sFieldName.substring(0, 1).toLowerCase()
                + sFieldName.substring(1);// 首字母转换为小写

        return sFieldName;
    }

    /**
     * 获得当前年份
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = sdf.format(new Date(System.currentTimeMillis()));
        return currDt.substring(0, 4);
        // return currDt;
    }


    /**
     * 替换不合法的字符串,在xml中使用
     *
     * @param str 原是字符串
     * @return 替换后的字符
     */
    public static String replace(String str) {
        byte[] illBytes = new byte[32];
        for (byte i = 0; i < 32; i++) {
            illBytes[i] = i;
        }
        String tmp = new String(illBytes);
        for (int i = 0; i < 32; i++) {
            if (i != 9 && i != 10 && i != 13) {
                str = str.replace(tmp.charAt(i), ' ');
            }
        }
        return str;
    }

    /**
     * 按分隔符分割字符
     *
     * @param str  原始字符
     * @param spec 分隔符
     * @return 分割后的字符数组
     */
    public static String[] str2array(String str, String spec) {
        return str2array(str, spec, false);
    }

    /**
     * 按分隔符分割字符
     *
     * @param str      原始字符
     * @param spec     分隔符
     * @param withNull 是否统计空字符串
     * @return 分割后的字符数组
     */
    public static String[] str2array(String str, String spec, boolean withNull) {
        if (isEmpty(str) && !withNull) {
            return new String[0];
        }
        if (isEmpty(str) && withNull) {
            return new String[]{""};
        }
        Vector vt = str2vector(str, spec, withNull);
        String[] ar = (String[]) vt.toArray(new String[0]);
        return ar;
    }

    /**
     * 按分隔符分割字符
     *
     * @param str      原始字符
     * @param spec     分隔符
     * @param withNull 是否统计空字符串
     * @return 分割后的字符链表
     */
    public static Vector str2vector(String str, String spec, boolean withNull) {
        if (str == null) {
            str = "";
        }
        Vector vt = new Vector();
        while (str.indexOf(spec) != -1) {
            String tmp = str.substring(0, str.indexOf(spec));
            if (withNull || !tmp.equals("")) {
                vt.addElement(tmp.trim());
            }
            str = str.substring(str.indexOf(spec) + spec.length());
        }
        if (!str.trim().equals("")) {
            vt.addElement(str.trim());
        }
        return vt;
    }

    /**
     * 判断一个字符是否是数值型
     *
     * @param value 要判断的字符
     * @return 如果为真则为数值型，反之为假。
     */
    public static boolean isInt(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            if ((value.charAt(i) >= '0' && value.charAt(i) <= '9')
                    || value.charAt(i) == '.') {
                count++;
            }
        }
        if (count == value.length()) {
            return true;
        }
        return false;
    }

    /**
     * 将字符整型转换成整形
     *
     * @param value 字符整型
     * @return 整型
     */
    public static int getIntValue(String value) {
        value = value.concat(".");
        return Integer.parseInt(value.substring(0, value.indexOf(".")));
    }

    /**
     * 将整型对象转换成整型
     *
     * @param value 整型对象
     * @return 整型
     */
    public static int getIntValue(Object value) {
        return getIntValue(value.toString());
    }

    /**
     * 从整型数组中抽取最大的整型
     *
     * @param intar 整型数组
     * @return 最大整型
     */
    public static int getMaxInt(int[] intar) {
        int index = 0;
        for (int i = 0; i < intar.length; i++) {
            if (intar[i] > index) {
                index = intar[i];
            }
        }
        return index;
    }

    /**
     * 取出文本文件的内容
     *
     * @param fileName 指定的文本文件的路径
     * @return 返回文本文件的内容
     * @throws Exception 抛出文档输入输出异常
     */
    public static String getTextFileContent(String fileName) throws Exception {
        byte[] dataBytes = getFileBytes(fileName);
        return toString(dataBytes);
    }

    /**
     * 取出文本文件的前部分内容
     *
     * @param fileName 指定的文本文件的路径
     * @param length   指定取的文件字节长度
     * @return 返回文本文件的前部分内容
     * @throws Exception 抛出文档输入输出异常
     */
    public static String getTextFileContent(String fileName, int length)
            throws Exception {
        byte[] dataBytes = getFileBytes(fileName, length);
        return toString(dataBytes);
    }

    /**
     * 取出文本文件的前部分内容
     *
     * @param fileName 指定的文本文件的路径
     * @param start    起始位置
     * @param length   指定取的文件字节长度
     * @return 返回文本文件的前部分内容
     * @throws Exception 抛出文档输入输出异常
     */
    public static String getTextFileContent(String fileName, int start,
                                            int length) throws Exception {
        byte[] dataBytes = getFileBytes(fileName, start, length);
        return toString(dataBytes);
    }

    /**
     * 将字节数组转换成有效字符
     *
     * @param src 字节数组
     * @return 有效字符串
     */
    public static String toString(byte[] src) {
        if (src == null || src.length == 0) {
            return "";
        }
        String tmp = null;
        if (src[0] < 0 && src.length == 1) {
            return "";
        }
        if (src.length == 1) {
            return new String(src);
        }
        if (src[0] < 0 && src[1] >= 0) {
            if (src[src.length - 1] > 0 && src[src.length - 2] < 0) {
                return new String(src, 1, src.length - 2);
            } else {
                return new String(src, 1, src.length - 1);
            }
        } else {
            if (src[src.length - 1] > 0 && src[src.length - 2] < 0) {
                return new String(src, 0, src.length - 1);
            } else {
                return new String(src, 0, src.length);
            }
        }
    }

    /**
     * 读取文件,变成Byte数组
     *
     * @param fileName 指定的文本文件的路径
     * @return 返回文件的bytes数组
     * @throws IOException 抛出文档输入输出异常
     */
    public static byte[] getFileBytes(String fileName) throws IOException {
        return getFileBytes(fileName, 0);
    }

    /**
     * 读取文件,变成Byte数组
     *
     * @param fileName 指定的文本文件的路径
     * @param start    起始位置
     * @param length   指定取的文件字节长度
     * @return 返回文件的bytes数组
     * @throws IOException 抛出文档输入输出异常
     */
    public static byte[] getFileBytes(String fileName, int start, int length)
            throws IOException {
        byte[] fBytes = getFileBytes(fileName);
        return midBytes(fBytes, start, length);
    }

    /**
     * 读取文件,变成Byte数组
     *
     * @param fileName 指定的文本文件的路径
     * @param length   指定取的文件字节长度
     * @return 返回文件的bytes数组
     * @throws IOException 抛出文档输入输出异常
     */
    public static byte[] getFileBytes(String fileName, int length)
            throws IOException {
        fileName = fileName.replace('\\', '/');
        mkdirs(fileName);
        File file1 = new File(fileName);
        if (!file1.exists()) {
            file1.createNewFile();
        }
        FileInputStream fileinputstream = new FileInputStream(file1);
        int fileLength = getIntValue(file1.length() + "");
        if (length <= 0 || length > fileLength) {
            length = fileLength;
        }
        return getStreamByte(fileinputstream, length);
    }

    /**
     * 根据文件路径，创建未存在的目录。
     *
     * @param filename 要创建的文件.
     */
    public static void mkdirs(String filename) {
        filename = filename.replace('\\', '/');
        if (filename.indexOf("/") == -1) {
            File dir = new File(filename);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } else {
            String dirPath = filename.substring(0,
                    filename.lastIndexOf("/") + 1);
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dirPath = filename.substring(filename.lastIndexOf("/") + 1);
            if (dirPath.indexOf(".") == -1) {
                dir = new File(filename);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
        }
    }

    /**
     * 取字节数组中任意位置任意长度的数组
     *
     * @param src    原始字节数组
     * @param start  起始位置
     * @param length 字节长度
     * @return 字节数组
     */
    public static byte[] midBytes(byte[] src, int start, int length) {
        if (start < 0) {
            start = 0;
        }
        if (length <= 0) {
            return null;
        }
        if (start >= src.length) {
            return null;
        }
        if (start + length > src.length) {
            length = src.length - start;
        }
        byte[] dBytes = new byte[length];
        System.arraycopy(src, start, dBytes, 0, length);
        return dBytes;
    }

    /**
     * 获取输入流数据
     *
     * @param is     输入流
     * @param length 输入流长度
     * @return 数据流
     * @throws IOException
     */
    public synchronized static byte[] getStreamByte(InputStream is, int length)
            throws IOException {
        if (is == null) {
            return null;
        }
        if (length < 0) {
            length = 0;
        }
        byte[] allBytes = new byte[length];
        DataInputStream in = null;
        in = new DataInputStream(is);
        int bytesRead = 0;
        int totalBytesRead = 0;
        int sizeCheck = 0;
        if (length > 0) {
            while (bytesRead != -1) {
                byte[] readBytes = new byte[8192];
                bytesRead = in.read(readBytes);
                if (bytesRead != -1) {
                    totalBytesRead += bytesRead;
                    if (totalBytesRead <= length) {
                        System
                                .arraycopy(midBytes(readBytes, 0, bytesRead),
                                        0, allBytes,
                                        totalBytesRead - bytesRead, bytesRead);
                    } else {
                        sizeCheck = totalBytesRead - length;
                        System.arraycopy(midBytes(readBytes, 0, bytesRead), 0,
                                allBytes, totalBytesRead - bytesRead, bytesRead
                                        - sizeCheck);
                        break;
                    }
                }
            }
            in.close();
            if (totalBytesRead < length) {
                return midBytes(allBytes, 0, totalBytesRead);
            } else {
                return allBytes;
            }
        }
        // 读未知长度数据流
        length = 2048 * 1024;
        allBytes = new byte[length];
        while (bytesRead != -1) {
            byte[] readBytes = new byte[8192];
            bytesRead = in.read(readBytes);
            if (bytesRead != -1) {
                totalBytesRead += bytesRead;
                if (totalBytesRead <= length) {
                    System.arraycopy(midBytes(readBytes, 0, bytesRead), 0,
                            allBytes, totalBytesRead - bytesRead, bytesRead);
                } else {
                    length += 2048 * 1024;
                    byte[] tmpBytes = allBytes;
                    allBytes = new byte[length];
                    System.arraycopy(tmpBytes, 0, allBytes, 0, tmpBytes.length);
                    System.arraycopy(midBytes(readBytes, 0, bytesRead), 0,
                            allBytes, totalBytesRead - bytesRead, bytesRead
                                    - sizeCheck);
                }
            }
        }
        in.close();
        return midBytes(allBytes, 0, totalBytesRead);
    }

    /**
     * 将一个字符转成InputStream
     *
     * @param str 要转换的字符
     * @return 返回InputStream
     * @throws IOException
     */
    public static ByteArrayInputStream getStringInputStream(String str)
            throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes());
        return bis;
    }

    /**
     * 获取一个用空格叠加的字符在给定字符串中第一次出现的位置
     *
     * @param src      给定字符串
     * @param prefix   叠加字符串前缀
     * @param postfix1 叠加字符串后缀1
     * @param postfix2 叠加字符串后缀2
     * @return 所在位置，-1为找不到该叠加字符串
     */
    public static int getIndexOf(String src, String prefix, String postfix1,
                                 String postfix2) {
        String temp = null;
        int index = 0;
        for (int i = 1; i < 5; i++) {
            temp = prefix + getSpace(i) + postfix1;
            index = src.indexOf(temp);
            if (index != -1) {
                return index;
            }
            if (!isEmpty(postfix2)) {
                temp = prefix + getSpace(i) + postfix2;
                index = src.indexOf(temp);
                if (index != -1) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * 获得诸如" "的空格；
     *
     * @param length 长度
     * @return 给定长度的空格
     */
    static public String getSpace(int length) {
        return getLengthChar(length, ' ');
    }

    /**
     * 获得字符串；
     *
     * @param length 长度
     * @param spec   字符char
     * @return 给定长度的空格
     */
    static public String getLengthChar(int length, char spec) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(spec);
        }
        return buffer.toString();
    }

    /**
     * 将字符串转换成html能识别的样式，即将html保留的字符进行替换
     *
     * @param src   源字符串
     * @param nl2br 是否将换行符转换成html的br换行符
     * @return 转换后html能识别的字符串
     */
    public static String str2Html(String src, boolean nl2br) {
        return str2Html(src, nl2br, true);
    }

    /**
     * 将字符串转换成html能识别的样式，即将html保留的字符进行替换
     *
     * @param src          源字符串
     * @param nl2br        是否将换行符转换成html的br换行符
     * @param replaceLtgt  是否替换大于小于号
     * @param replaceSpace 是否替换空格
     * @return 转换后html能识别的字符串
     */
    public static String str2Html(String src, boolean nl2br,
                                  boolean replaceLtgt, boolean replaceSpace) {
        if (src == null) {
            return null;
        }
        src = replace(src, "&", "&amp;");
        src = replace(src, "\"", "&quot;");
        if (replaceLtgt) {
            src = replace(src, "<", "&lt;");
            src = replace(src, ">", "&gt;");
        }
        if (nl2br) {
            src = replace(src, "\n", "\n<br>");
        }
        if (replaceSpace) {
            src = replace(src, " ", "&nbsp;");
            src = replace(src, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        return src;
    }

    /**
     * 将字符串转换成html能识别的样式，即将html保留的字符进行替换
     *
     * @param src 原字符串
     * @return 替换后的字符串
     */
    public static String str2EditHtml(String src) {
        return str2Html(src, false, true, false);
    }

    /**
     * 将字符串转换成html能识别的样式，即将html保留的字符进行替换
     *
     * @param src 原字符串
     * @return 替换后的字符串
     */
    public static String editHtml2Str(String src) {
        src = replace(src, "&amp;", "&");
        src = replace(src, "&quot;", "\"");
        src = replace(src, "&lt;", "<");
        src = replace(src, "&gt;", ">");
        return src;
    }

    /**
     * 将字符串转换成html能识别的样式，即将html保留的字符进行替换
     *
     * @param src         源字符串
     * @param nl2br       是否将换行符转换成html的br换行符
     * @param replaceLtgt 是否替换大于小于号
     * @return 转换后html能识别的字符串
     */
    public static String str2Html(String src, boolean nl2br, boolean replaceLtgt) {
        return str2Html(src, nl2br, replaceLtgt, true);
    }

    /**
     * 将字符串转换成html能识别的样式，即将html保留的字符进行替换，不将换行符转换成html的br换行符，这在多行编辑器中是有用的
     *
     * @param src 源字符串
     * @return 转换后html能识别的字符串
     */
    public static String str2Html(String src) {
        return str2Html(src, true);
    }

    /**
     * 获取字符流中字符串
     *
     * @param is          字符流
     * @param charsetName 编码
     * @return 字符串
     * @throws IOException
     */
    public synchronized static String getStreamString(InputStream is,
                                                      String charsetName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                charsetName));
        StringBuffer sb = new StringBuffer(2048);
        String tmp = br.readLine();
        while (tmp != null) {
            sb.append("\r\n" + tmp);
            tmp = br.readLine();
        }
        return sb.toString().substring(2);
    }


    /**
     * 根据默认编码转化值，如果为空，返回默认值
     *
     * @param value        要转换的值
     * @param defaultValue 默认值
     * @param encoding     是否进行代码转换
     * @return 按默认编码转化值
     */
    public static String getStringValue(String value, String defaultValue,
                                        boolean encoding) {
        String tmp = value;
        if (encoding) {
            tmp = encoding(value, "GBK");// default encoding
        }
        if (isEmpty(tmp)) {
            return defaultValue;
        }
        return tmp;
    }

    /**
     * 根据默认编码转化值，如果为空，返回默认值
     *
     * @param value        要转换的值
     * @param defaultValue 默认值
     * @return 按默认编码转化值
     */
    public static String getStringValue(String value, String defaultValue) {
        return getStringValue(value, defaultValue, true);
    }

    /**
     * 根据默认编码转化值，如果为空，返回空字符
     *
     * @param value 要转换的值
     * @return 按默认编码转化值
     */
    public static String getStringValue(String value) {
        return getStringValue(value, "");
    }

    /**
     * 按char型分隔符分割字符,将所给分隔符变成拆分成多个char,然后分别分割
     *
     * @param str  原始字符
     * @param spec 分隔符
     * @return 分割后的字符数组
     */
    public static String[] strSplit(String str, String spec) {
        return strSplit(str, spec, false);
    }

    /**
     * 按char型分隔符分割字符,将所给分隔符变成拆分成多个char,然后分别分割
     *
     * @param str      原始字符
     * @param spec     分隔符
     * @param withNull 是否统计空字符串
     * @return 分割后的字符数组
     */
    public static String[] strSplit(String str, String spec, boolean withNull) {
        if (isEmpty(str)) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(str, spec);
        int count = token.countTokens();
        Vector vt = new Vector();
        for (int i = 0; i < count; i++) {
            String tmp = token.nextToken();
            if (withNull || !isEmpty(tmp)) {
                vt.addElement(tmp.trim());
            }
        }
        return (String[]) vt.toArray(new String[0]);
    }

    /**
     * 以encoding的编码方式对字符串进行重新编码
     *
     * @param val       原始字符串
     * @param encoding  编码
     * @param strCoding 字符编码
     * @return 字符串
     */
    protected static String encoding(String val, String encoding,
                                     String strCoding) {
        if (isEmpty(val)) {
            return val;
        }
        if (!isEmpty(encoding)) {
            try {
                if (!isEmpty(strCoding)) {
                    val = new String(val.getBytes(encoding), strCoding);
                } else {
                    val = new String(val.getBytes(encoding));
                }
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }
        }
        return val;
    }

    /**
     * 以encoding的编码方式对字符串进行重新编码
     *
     * @param val      原始字符串
     * @param encoding 编码
     * @return 字符串
     */
    public static String encoding(String val, String encoding) {
        return encoding(val, encoding, "");
    }

    /**
     * 判断一个类是否为某个接口
     *
     * @param cls            类
     * @param sInterfaceName 接口名
     * @return
     */

    public static boolean validateInterface(Class cls, String sInterfaceName) {
        // if(validateInterface(target.getClass(),"com.iss.szair.crm.IScoreBiz"))
        boolean bool = false;
        Class[] arrayClass = cls.getInterfaces();
        for (int i = 0; i < arrayClass.length; i++) {
            if (arrayClass[i].toString().endsWith(sInterfaceName)) {
                bool = true;
                break;
            }
        }

        return bool;
    }

    public static String convertObjArray2Str(Object[] ob) {
        int length = ob.length;
        String[] str1 = new String[length];
        String sTmp = "";
        for (int i = 0; i < ob.length; i++) {
            if (ob[i] != null) {
                str1[i] = ob[i].toString();
                sTmp += str1[i] + " ";
                logger.info(str1[i]);
            } else {
                str1[i] = null;
            }
        }
        logger.info("转换完毕");
        logger.info(sTmp);
        return sTmp.trim();


    }

    /**
     * 返回某一个日期的相对日期，例如2009-01-01的后45天的日期,尚未测试
     *
     * @return
     */
    public static String getRelativeDate(String datestr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        java.sql.Date olddate = null;
        try {
            df.setLenient(false);
            olddate = new java.sql.Date(df.parse(datestr).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("日期转换错误");
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(olddate);

        int Year = cal.get(Calendar.YEAR);
        int Month = cal.get(Calendar.MONTH);
        int Day = cal.get(Calendar.DAY_OF_MONTH);

        int NewDay = Day + day;

        cal.set(Calendar.YEAR, Year);
        cal.set(Calendar.MONTH, Month);
        cal.set(Calendar.DAY_OF_MONTH, NewDay);
        Date dt = new java.sql.Date(cal.getTimeInMillis());
        return df.format(dt);

    }

    /**
     * 返回yyyy年MM月dd日 星期一格式的字符串
     *
     * @return
     */
    public static String getCurrYmdWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String datestr = sdf.format(new Date());
        // 在linux环境下会出现英文星期的三位字母，所以以中文替代
        datestr = datestr.replace("Mon", "星期一");
        datestr = datestr.replace("Tue", "星期二");
        datestr = datestr.replace("Wed", "星期三");
        datestr = datestr.replace("Thu", "星期四");
        datestr = datestr.replace("Fri", "星期五");
        datestr = datestr.replace("Sat", "星期六");
        datestr = datestr.replace("Sun", "星期日");
        return datestr;

    }

    /**
     * 传入 yyyy-mm-dd
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    // 一个中文字符在oracle(UTF8字符集）是按3个字符长度计算的，而不是2个字节
    public static int getOracleStrLen(String str) {
        int i = 0;
        byte b[] = str.getBytes();
        int byteLength = b.length; // 总字节长度
        int strLength = str.length(); // 字符串长度
        int chineseCount = byteLength - strLength;// 中文是双字节，所以差值为中文个数
        int otherCount = byteLength - chineseCount * 2;// 单字符个数
        int oracleByteCount = chineseCount * 3 + otherCount;// 在UTF8
        // oracle中文按3字节,再加上非中文的字符个数
        return oracleByteCount;
    }


    public static String gbk2utf(String gbk) {
        String utf8 = "";
        try {
            utf8 = new String(gbk2utf8(gbk), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8;
    }

    public static byte[] gbk2utf8(String chenese) {
        char c[] = chenese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++) {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }


    /**
     * 得到网页中图片的地址
     */
    // 重点在于正则表达式 <img.*src=(.*?)[^>]*?>
    // src=\"?(.*?)(\"|>|\\s+)
    public static List<String> getImgStr(String htmlStr) {
        //reg = /<[img|href][^>]*src\s*=\s*('|")?([^'">]*)\1([^>])*>/ig
        //reg = /<[img|href][^>]*src\s*=\s*('|")?([^'">]*)\1([^>])*>/ig
        ///<[img|href][^>]*src\s*=\s*('|")?([^'">]*)\1([^>])*>/ig

        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();
        //下面被注释掉的部分可能会导致下载2次图片
		/*String regEx_img = "<img.*src=(.*?)[^>]*?>"; // 图片链接地址
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find())
		{
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); // 匹配src
			while (m.find()) {
				pics.add(m.group(1));
			}
		}*/

        //改用下面的正则表达式测试是否会下载2次
        String regEx_img1 = "<img.*?src=\"(.*?)\"";
        p_image = Pattern.compile(regEx_img1, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            //logger.info("解析::::::"+m_image.group(1));
            pics.add(m_image.group(1));
            //logger.info("正则解析图片连接:"+m_image.group(1));
        }
        //增加img src=' ----未测试 --2022-02-06
        regEx_img1 = "<img.*?src='(.*?)'";
        p_image = Pattern.compile(regEx_img1, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // logger.info("解析::::::"+m_image.group(1));
            pics.add(m_image.group(1));
            // logger.info("正则解析图片连接:"+m_image.group(1));
        }


        return pics;
    }

    /**
     * 将转义的html字符串进行反转--是否有更好的替换方式
     *
     * @param value
     * @return
     */
    public static String deccodeHtmlString(String value) {
        String sReturn = "";
        sReturn = value.replace("&lt;", "<");// 小于号
        sReturn = sReturn.replace("&gt;", ">");// 大于号
        sReturn = sReturn.replace("&amp;", "&");// &
        sReturn = sReturn.replace("&quot;", "\"");// 双引号
        // sReturn = sReturn.replace("&reg;", "");//已注册
        // sReturn = sReturn.replace("&copy;", "");//版权
        // sReturn = sReturn.replace("&trade;", "");//版权
        sReturn = sReturn.replace("&ensp;", " ");// 半个空白位,很少用,是否为 " "未确定
        sReturn = sReturn.replace("&emsp;", "  ");// 半个空白位,很少用,是否为 " "未确定
        sReturn = sReturn.replace("&nbsp;", " ");// 半个空白位,很少用,是否为 " "未确定
        return sReturn;
    }


    /**
     * 传入汉字串，获取每个汉字的首字母
     *
     * @return
     */
    public static String getFirstPinyin(String cnStr) {
        String sReturn = "";
        if (cnStr != null && cnStr.length() > 0) {
            for (int i = 0; i < cnStr.length(); i++) {
                String sTmp = cnStr.substring(i, i + 1);
                logger.info("逐个显示字符串:" + sTmp);
                String pinyin = CnToSpell.getFullSpell(sTmp);

                if (pinyin != null && pinyin.length() > 0) {
                    sReturn += pinyin.substring(0, 1);

                }
            }
        }
        logger.info(cnStr + "的首字母:" + sReturn);
        //char[] chars = cnStr.toCharArray();
        //for (int i = 0; i < chars.length; i++)
        //{

        //String tmp = CnToSpell.getFullSpell(chars[i]);

        //}
        return sReturn;

    }


    /**
     * 查询srcStr字符串中includeStr子串的重复次数
     *
     * @param srcStr
     * @param includeStr
     * @return
     */
    public static int subStrCount(String srcStr, String includeStr) {

        if (srcStr == null || includeStr == null || srcStr.trim().length() == 0 || includeStr.trim().length() == 0) {
            return 0;
        }
        char[] c1;
        char[] c2;
        c1 = srcStr.toCharArray();
        c2 = includeStr.toCharArray();
        int count = 0;
        int t;
        int m = srcStr.length() - includeStr.length();
        if (m < 0) {
            //System.out.println("字符串中不含"+includeStr+"子串");
        } else {
            for (int i = 0; i < srcStr.length(); i++) {
                t = 0;
                while (t < includeStr.length() && c2[t] == c1[i + t]) {
                    t++;
                }
                if (t == includeStr.length()) count++;
            }
        }
        return count;
    }

    /**
     * 返回不带间隔符的14位年月日时分秒
     *
     * @return
     */
    public static String getCurrentYmd3() {
        //当前时分秒，不带-

        //String currDt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //		.format(new Date(System.currentTimeMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String currDt = "";
        currDt = sdf.format(new Date(System.currentTimeMillis()));
        return currDt;


    }




    public static String getRandom(long longValue, int digit) {
        //longValue备用，本方法默认为1000000
        String sRand = "";
        //获取digit位随机码


        double rand = Math.random();
        int i = (int) (rand * longValue);
        sRand = "00000000000000000000" + String.valueOf(i);
        sRand = sRand.substring(sRand.length() - digit);
        //long i = (long)(Math.pow(10, longValue));
        //sRand = "00000000000000000000"+String.valueOf(i);
        //sRand = sRand.substring(sRand.length()-digit);
        return sRand;

    }



    /**
     * 获取指定日期时间后多少分钟之后的时间
     *
     * @param dt      日期
     * @param minutes 分钟数
     * @return
     * @throws ParseException
     */
    public static String getRelateMin(String dt, long minutes) throws ParseException {
        String sReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        Date currDt = sdf.parse(dt);
        Long currTime = currDt.getTime();
        currTime = currTime + minutes * 60 * 1000;//换算成毫秒
        sReturn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(currTime));
        return sReturn;
    }

    /**
     * @param dt      yyyy-MM-dd HH:mm:ss
     * @param seconds 秒数
     * @return
     * @throws ParseException
     */
    public static String getRelateSecond(String dt, long seconds) throws ParseException {
        String sReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        Date currDt = sdf.parse(dt);
        Long currTime = currDt.getTime();
        currTime = currTime + seconds * 1000;//换算成毫秒
        sReturn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currTime));
        return sReturn;
    }

    /**
     * 将cst格式的日期时间字符串转换为日期时分秒格式---这个转换会不会有时区问题，谨慎使用
     * @param cstDt
     * @return
     */
	/*public static String cst2ymd(String cstDt)
	{
		//String dateStr = "Fri Apr 11 14:01:32 CST 2008";
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dateStr = dateFormat.format(new Date(cstDt));
    	return dateStr;
	}*/

    /**
     * 获取指定日期yyyy-MM-dd的上周周日的日期 yyyy-MM-dd
     *
     * @param currDateTime    当前日期时分秒或当前日期 yyyy-MM-dd
     * @param relativeWeekNum 为推迟的周数，0本周，-1向前推迟一周，2下周，依次类推
     * @param weekCode        0星期日 1 星期1 一直到 6 星期 六
     * @return
     * @throws ParseException
     */
    public static String getPriorWeek(String currDateTime, int relativeWeekNum, int weekCode) throws ParseException {
        String sReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currDt = sdf.parse(currDateTime.substring(0, 10));//获取日期
        Calendar cal = Calendar.getInstance();
        //设置日历的日期
        cal.setTime(currDt);
        cal.add(Calendar.DATE, relativeWeekNum * 7);
        //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        if (weekCode == 0) cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        if (weekCode == 1) cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (weekCode == 2) cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        if (weekCode == 3) cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        if (weekCode == 4) cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        if (weekCode == 5) cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        if (weekCode == 6) cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        sReturn = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return sReturn;
    }

    /**
     * 简繁体互相转换
     *
     * @param srcContent
     * @param srcType    两种，BIG5代表繁体 GB代表简体
     * @param destType   两种，BIG5代表繁体 GB代表简体
     * @param request
     * @return
     */
    public static String convertBig5(String srcContent, String srcType, String destType, HttpServletRequest request) {
        String sReturn = srcContent;
        if ("GB".equals(srcType) && "BIG5".equals(destType)) {
            try {
                sReturn = new String(GB2Big5.getInstance().gb2big5(srcContent), "BIG5");
                // outStr = new String(pTmp.gb2big5(inStr),"BIG5");
            } catch (Exception ex) {
                logger.error("StringUtil简体转繁体异常...");
            }
        } else if ("BIG5".equals(srcType) && "GB".equals(destType)) {
            try {
                sReturn = GB2Big5.getInstance().big52gb(srcContent);
            } catch (Exception ex) {
                logger.error("StringUtil简体转繁体异常...");
            }

        } else {
            sReturn = srcContent;//其他情况不做处理
        }
        return sReturn;
    }

    /**
     * 将日期型转换为日期时分秒
     *
     * @param date 日期
     * @return
     */
    public static String date2Str(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currDt = sdf.format(date);
        return currDt;

    }


    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 获取某个网页的正文内容
     *
     * @param urlAddr
     * @return
     */
    public static String getUrlContent(String urlAddr, String encoding) {
        HttpClient client = new HttpClient();
		 /*HttpUriRequest request  = new HttpGet(url);
		 HttpEntity httpEntity = null;
		  request.addHeader("Accept-Charset", "utf-8");
		  request.addHeader("Host", HOST);
		  request.addHeader("Accept", ACCEPT);
		 request.addHeader("User-Agent", USER_AGENT);
		 http://my.oschina.net/dongwq/blog/188094
		 */
        //http://www.jb51.net/article/46850.htm
        //http://blog.csdn.net/b280889189/article/details/12157005
        //http://blog.csdn.net/b280889189/article/details/12157005
        String respText = "";
        HttpMethod method = new GetMethod(urlAddr);
        //Header locationHeader = method.getResponseHeader(  "location");
         /*Header locationHeader = method.getResponseHeader(  "http://www.culturalink.gov.cn/portal/test/listen.jsp");
         logger.info(locationHeader.getValue());
         client.getParams().setParameter("http.socket.timeout",
                 new Integer(60000 * 10));
         logger.info("b.............");
         Header host = method.getRequestHeader("host");
         logger.info("c.............");
         method = new GetMethod("http://" + host.getValue() +    locationHeader.getValue());
         logger.info("d.............");

         method.setFollowRedirects(false);
         logger.info("e.............");*/

        //HttpMethod method=new GetMethod("http://127.0.0.1:8088/portal/test/listen.jsp");
        //尝试处理org.apache.http.client.CircularRedirectException异常
        //method.getParams().setParameter("http.protocol.allow-circular-redirects", true);
        //method.setFollowRedirects(false) ;

        //method.setFollowRedirects(false) ;//重定向处理
        ////HttpMethod method = new PostMethod("http://java.sun.com");

        try {
            client.executeMethod(method);
            respText = method.getResponseBodyAsString();

            method.releaseConnection();
        } catch (Exception ex) {
            logger.error("监听异常:" + ex.toString());
        }
        //respText = getRedirectInfo("http://www.culturalink.gov.cn/portal/test/listen.jsp?a=2");

        return respText;
    }

    /**
     * 根据html正文返回
     *
     * @param htmlText
     * @return
     */
    public static List<String> getHrefList(String htmlText) {
        List<String> list = new ArrayList<String>();
        String regEx = "<a.*?href=\"(.*?)\"";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(htmlText);
        while (mat.find()) {
            String url = mat.group(1);
            //String tmpImgUrl = imgUrl.toLowerCase();
            list.add(url);
        }
        return list;
    }

    public static String getHttpRootDomain(String url) {
        String rootDomain = "";
        try {
            Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            matcher.find();
            rootDomain = matcher.group();
        } catch (Exception ex) {
        }

        return rootDomain;
    }

    /**
     * 返回两个日期之间相差的天数
     *
     * @param dtFrom
     * @param dtTo
     * @return
     * @throws ParseException
     */
    public static int getRelativeDays(String dtFrom, String dtTo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(dtFrom));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(dtTo));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 获取两个字符串日期相隔的描述 格式 2018-01-01 00:00:00
     *
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     */
    public static int getRelateSeconds(String time1, String time2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = format.parse(time2);
        Date date1 = format.parse(time1);
        int seconds = (int) ((date2.getTime() - date1.getTime()) / 1000);
        return seconds;

    }

    public static Map<String, Double> getMaxMin(double[] numArray) {
        double max = numArray[0];
        double min = numArray[1];

        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] > max) {
                max = numArray[i];
            } else if (numArray[i] < min) {
                min = numArray[i];
            }
        }
        Map<String, Double> map = new HashMap();
        //System.out.println("最大值:"+max+",最小值:"+min);
        map.put("min", min);
        map.put("max", max);

        return map;
    }

    public static Map<String, Long> getMaxMin(long[] numArray) {
        long max = numArray[0];
        long min = numArray[1];

        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] > max) {
                max = numArray[i];
            } else if (numArray[i] < min) {
                min = numArray[i];
            }
        }
        Map<String, Long> map = new HashMap();
        //System.out.println("最大值:"+max+",最小值:"+min);
        map.put("min", min);
        map.put("max", max);

        return map;
    }

    public static String getIpAddress(HttpServletRequest request) {


        //此方法需要进一步验证，确认是否符合各种业务场景。
        //String ip = request.getHeader("X-Forwarded-For"); //空，未使用nginx
        String ip = request.getHeader("x-Forwarded-For"); //空，未使用nginx
		/*
		 *
		 * X-Forwarded-For是用于记录代理信息的,每经过一级代理，该字段就会记录来源地址,经过多级代理，服务端就会记录每级代理的X-Forwarded-For信息。

例：从客户端发送请求发送到代理后，代理的X-Forwarded-For是客户端的ip。

把代理的请求再发送到服务端，服务端收到的X-Forwarded-For是代理的ip。

而X-Real-IP，一般只记录真实发出请求的客户端IP。

		 * */
        //logger.info("StringUtil获取IP开始................");
        //logger.info("x-Forwarded-For::");
        //logger.info(ip);//127.0.0.1, 140.250.109.10  有代理的时候
        //logger.info("x-Forwarded-For::");
        //logger.info(request.getHeader("X-Forwarded-For"));
        //logger.info("StringUtil获取IP结束................");

        //可能还有其他的方式的。

        String ip1 = request.getHeader("X-Real-IP");//空，未使用nginx
        String ip2 = request.getRemoteAddr(); //tomcat ip

        String ip0 = "";
        //先判断真实IP.... X-Real-IP
        if (ip1 != null && ip1.trim().length() > 0) {
            //logger.info("按X-Real-IP获取......");
            //logger.info(ip1);
            ip0 = ip1;
        } else if (ip != null && ip.trim().length() > 0) {
            // 172.1.3.37
            //logger.info("按x-Forwarded-For 获取IP ::::");
            //logger.info(ip);
            ip0 = ip;
        } else if (ip2 != null && ip2.trim().length() > 0) {

            ip0 = ip2;
        }

        if (ip0.contains(",")) { //127.0.0.1, 140.250.109.10 这个应该取后面的吧？
            //ip0  =ip0.split(",")[0];
            //ip0  =ip0.split(",")[0];
            String[] tmpIp = ip0.split(",");
            ip0 = tmpIp[tmpIp.length - 1];
        }
        return ip0;
    }

    /**
     * 生成默认密码
     *
     * @param pwdLength
     * @param pwdType
     * @param isEncode
     * @return
     */
    public static String createPassword(int pwdLength, String pwdType, boolean isEncode, String key) throws Exception {
        String[] upperArray = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] lowerArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] digitArray = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] otherArray = new String[]{"@", "-", "_", "*", "#", "~"};
        //按照大小写和特殊字符
        String pwd = "";
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasOther = false;//特殊字符
        int sumLen = upperArray.length + lowerArray.length + 10 + otherArray.length;//
        if (pwdLength < 6) {
            throw new Exception("密码至少为6位!");
        }

        //如果不符合规则则继续生成


        for (int i = 0; i < pwdLength; i++) {
            double rand = Math.random();
            int selNum = new Double(rand * sumLen).intValue();
            if (i == pwdLength - 4 && !hasUpper) //如果前面没生成大写字母，则倒数第四位需要为大写
            {
                selNum = new Double(rand * 26).intValue();

            }
            if (i == pwdLength - 3 && !hasLower) //
            {
                selNum = 26 + new Double(rand * 26).intValue();

            }
            if (i == pwdLength - 2 && !hasDigit) //
            {
                selNum = 52 + new Double(rand * 10).intValue();

            }
            if (i == pwdLength - 1 && !hasOther) //
            {
                //logger.info("最后一位取特殊字符........");
                selNum = 62 + new Double(rand * otherArray.length).intValue();

            }

            if (selNum >= 0 && selNum < 26) {
                hasUpper = true; //有大写

            } else if (selNum >= 26 && selNum < 52) {
                hasLower = true;//有小写

            } else if (selNum >= 52 && selNum < 62) {
                hasDigit = true;//有数字

            } else {

                hasOther = true;

            }

            if (selNum < 26) {
                pwd += upperArray[selNum];
            } else if (selNum >= 26 && selNum < 52) {
                pwd += lowerArray[selNum - 26];
            } else if (selNum >= 52 && selNum < 62) {
                pwd += digitArray[selNum - 52];
            } else {
                //logger.info("selNum大于62位:");
                //logger.info(selNum);
                pwd += otherArray[selNum - 62];
            }


        }

        if (isEncode) {
            //如果加密
            if ("MD5".equals(pwdType)) {
                pwd = Password.MD5EncodePassEncoding(pwd, "utf-8");
            } else if ("AES".equals(pwdType)) {
                pwd = AESUtil.aesEncode(key, pwd);
            }

        }


        return pwd;
    }

    /**
     * 从26个字母中生成指定位数的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String result = buffer.toString();
        return result;//从26个字母中生成随机字符串

    }

    /**
     * 带字母及数字的随机生成
     *
     * @param length
     * @return
     */
    public static String getRandomStrNum(int length) {

        char[] randArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = (int) (random.nextFloat() * 36);
            buffer.append(randArray[randomLimitedInt]);
        }
        String result = buffer.toString();
        return result;//从26个字母中生成随机字符串

    }


    public static List getRandArray(long arrayLen) {
        List list = new ArrayList();
        List list2 = new ArrayList();

        try {


            for (int i = 0; i < arrayLen; i++) {
                list.add(i + 1);
            }
            //检查list
            //System.out.println("检查list.......");
            for (int i = 0; i < list.size(); i++) {
                //System.out.println(list.get(i));
            }
            //System.out.println("------------------------");
            //开始随机排列

            while (list.size() > 0) {
                int index = (int) (Math.random() * list.size());
                //System.out.println("下标:");
                //System.out.println(index);
                list2.add(list.get(index));
                list.remove(index);

            }
            if (list2.size() > 0) {
                //System.out.println("youzhi...");
                for (int i = 0; i < list2.size(); i++) {
                    //System.out.println(list2.get(i));
                }
            } else {
                System.out.println("无值.....");
            }


        } catch (Exception ex) {
        }
        return list2;
    }

    /**
     * 从一个给定的数组中随机抽取几个不同的值构成新的数组
     *
     * @param parmList
     * @param getCount
     * @return
     */
    public static Object[] getRandArray(Object[] parmList, int getCount) {
        if (parmList == null || parmList.length == 0) return null;//如果为空则返回
        List tmpList = StringUtil.getRandArray(parmList.length);
        if (tmpList != null && tmpList.size() > 0) {
            //System.out.println("数字下标随机 ：");
            for (int i = 0; i < tmpList.size(); i++) {
                System.out.println(tmpList.get(i));
            }
            //System.out.println("======================");

        }
        //System.out.println("parmList:");
        for (int i = 0; i < parmList.length; i++) {
            //System.out.println(String.valueOf(i)+":"+parmList[i]);
        }
        //List<Object> newList =  new ArrayList();
        Object[] newArray = new Object[getCount];
        for (int i = 0; i < getCount; i++) {
            long sortNo = Long.parseLong(String.valueOf(tmpList.get(i)));

            newArray[i] = parmList[((int) sortNo) - 1];
            //System.out.println);

        }
        //System.out.println("++++++++++++++++++++");
        return newArray;//

    }


    public static byte[] hexTobytes(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        char[] hexChars = hex.toCharArray();
        byte[] bytes = new byte[hexChars.length / 2];   // 如果 hex 中的字符不是偶数个, 则忽略最后一个

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
        }

        return bytes;
    }

    /**
     * 从
     *
     * @param names 逗号分隔参数名，随机取出其中一个
     * @return
     */
    public static String getRandName(String names) {
        String[] array = names.split(",");
        double rand = Math.random();
        int len = array.length - 1;//3
        //System.out.println(len);

        //System.out.println("len:"+len);
        int irand = (int) Math.round((rand * len));

        String selStr = array[irand];
        //System.out.println(irand+"---"+selStr);


        return selStr.trim();

    }

    /**
     * 随机百分比
     *
     * @param randCount
     * @return
     */
    public static int[] getRandIntRatio(int randCount) {
        int[] rands = new int[randCount];
        int remain = 100;
        for (int i = 0; i < randCount; i++) {
            if (remain == 0) {
                rands[i] = 0;
            } else if (i == randCount - 1) {
                //最后一个全部分配
                rands[i] = remain;
            } else {
                int tmpRand = (int) (remain * Math.random());

                rands[i] = tmpRand;

                remain = remain - tmpRand;

            }

            System.out.println(rands[i]);
        }
        return rands;
    }




    /**
     * 获取随机手机号
     *
     * @return
     */
    public static String getRandMobile() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        sb.append(random.nextInt(9) + 3); // 3-9
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        String s = sb.toString();
        String firstNo = StringUtil.getRandName("138,139,153,136,137,171,172,186,192,191");
        s = firstNo + s.substring(3);
        return s;
    }


    /**
     * 返回html的正文内容--增加htmlparse依赖，总工程增加依赖后并maven update
     *
     * @param htmlContent
     * @return
     * @throws Exception
     */
    public static String getHtmlText(String htmlContent) throws Exception {

        if (htmlContent == null)
            htmlContent = "";
        // 增加一个<br/>,经测试，如果正文为纯文本,org.htmlparser会把参数当作一个文件处理
        StringBuffer sbf = new StringBuffer("");
        sbf.append("<br />").append(htmlContent);
        Parser parser = new Parser(sbf.toString());

        TextExtractingVisitor visitor = new TextExtractingVisitor();

        parser.visitAllNodesWith(visitor);

        String sReturn = visitor.getExtractedText();

        sReturn = sReturn.replace(" ", "");// 去掉空格为统计字数
        sReturn = sReturn.replace("\r", "");
        sReturn = sReturn.replace("\n", "");
        if (sReturn == null)
            sReturn = "";// wbz add at 2011-12-12

        return sReturn;
    }


}
