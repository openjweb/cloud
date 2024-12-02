package org.openjweb.common.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
//import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.io.FileUtils;//commons-io
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {
    private static final Logger logger = LogManager.getLogger(FileUtil.class);

    public FileUtil() {

    }

    /**
     * 创建多层级目录
     *
     * @param pathStr
     * @return
     */
    public static boolean mkdir(String pathStr) {
        File f = new File(pathStr);
        if (!f.exists()) {
            return f.mkdirs();
        } else {
            return true;
        }
    }

    public static boolean copy(String oldPath, String newPath) {

        boolean returnVal = false;

        try {

            FileInputStream fin = new FileInputStream(oldPath);

            FileOutputStream fout = new FileOutputStream(newPath);
            byte b[] = new byte[1];
            while (fin.read(b) != -1) {

                fout.write(b);

            }

            fin.close();

            fout.close();

            returnVal = true;

        }

        catch (Exception e) {

            e.printStackTrace();

            returnVal = false;

        }

        return returnVal;

    }

    public static boolean copy(String oldPath, String oldName, String newPath, String newName) {
        // src/java后直接接文件名，在哪里漏掉了/
        if (!oldPath.endsWith("/"))
            oldPath += "/";
        if (!newPath.endsWith("/"))
            newPath += "/";
        boolean returnVal = false;

        try {

            // System.out.println(oldPath+"/"+oldName+"; "+newPath+"/"+newName);

            File infile = new File(oldPath, oldName);
            try {
                mkdir(newPath);
            } catch (Exception ex) {
            }

            File outfile = new File(newPath, newName);

            FileInputStream fin = new FileInputStream(infile);

            FileOutputStream fout = new FileOutputStream(outfile);

            byte b[] = new byte[1];

            while (fin.read(b) != -1) {

                fout.write(b);

            }

            fin.close();

            fout.close();

            returnVal = true;

        }

        catch (Exception e) {

            e.printStackTrace();

            returnVal = false;

        }

        return returnVal;

    }

    public static boolean copy(String oldName, String newPath, String newName) {

        boolean returnVal = false;

        try {

            File infile = new File(oldName);

            if (mkdir(newPath)) {

                File outfile = new File(newPath, newName);

                FileInputStream fin = new FileInputStream(infile);

                FileOutputStream fout = new FileOutputStream(outfile);

                byte b[] = new byte[1];

                while (fin.read(b) != -1) {

                    fout.write(b);

                }

                fin.close();

                fout.close();

                returnVal = true;

            }

        }

        catch (Exception e) {

            e.printStackTrace();

            returnVal = false;

        }

        return returnVal;

    }

    /**
     * 复制一个目录下的所有文件到新的目录,不复制子目录
     *
     * @param oldPath 源路径
     * @param newPath 目标路径
     * @return
     */
    public static boolean copyFiles(String oldPath, String newPath) {

        boolean returnVal = false;

        try {

            File[] fileList;

            File odir = new File(oldPath);

            mkdir(newPath);

            if (odir.exists() && odir.isDirectory()) {

                fileList = odir.listFiles();

                for (int i = 0; i < fileList.length; i++) {

                    // System.out.println("listfiles:" + fileList[i].getPath() +
                    // "name:" +

                    // fileList[i].getName() + "," +
                    //
                    // fileList[i].isDirectory());

                    if (fileList[i].isFile()) {

                        returnVal = copy(oldPath, fileList[i].getName(), newPath,

                                fileList[i].getName());

                        if (!returnVal) {
                            logger.error("复制失败:" + fileList[i].getAbsolutePath());
                            return false;

                        }

                    }

                    else if (fileList[i].isDirectory()) {

                    }

                }

            }

            else if (odir.exists() && odir.isFile()) {

                return false;

            }

        }

        catch (Exception e) {

            e.printStackTrace();

            returnVal = false;

        }

        return returnVal;

    }

    public static boolean deleteFile(String path) {

        File f = new File(path);

        if (f.exists()) {

            return f.delete();

        }

        else {

            return true;

        }

    }

    /**
     * 删除指定目录下的文件或目录
     *
     * @param path
     * @param name
     * @return
     */
    public static boolean deleteFile(String path, String name) {

        File f = new File(path, name);

        if (f.exists()) {

            return f.delete();

        }

        else {

            return true;

        }

    }

    /**
     * 递归删除目录，即目录下有文件和子目录都删除
     *
     * @param Path
     * @return
     */
    public static boolean deleteDir(String Path) {

        boolean returnVal = false;

        File[] fileList;

        File mydir = new File(Path);

        try {

            if (mydir.exists() && mydir.isDirectory()) {

                fileList = mydir.listFiles();

                for (int i = 0; i < fileList.length; i++) {

                    // System.out.println("listfiles:" + fileList[i].getPath() +
                    // "name:" +

                    // fileList[i].getName() + "," +

                    // fileList[i].isDirectory());

                    if (fileList[i].isFile()) {

                        fileList[i].delete();

                    }

                    else if (fileList[i].isDirectory()) {

                        deleteDir(fileList[i].getPath());

                    }

                }

                mydir.delete();

            }

            else if (mydir.exists() && mydir.isFile()) {

                mydir.delete();

            }

            returnVal = true;

        }

        catch (Exception e) {

            e.printStackTrace();

            returnVal = false;

        }

        return returnVal;

    }

    public static boolean deleteDirFiles(String Path) {

        boolean returnVal = false;

        File[] fileList;

        File mydir = new File(Path);

        try {

            if (mydir.exists()) {

                fileList = mydir.listFiles();

                for (int i = 0; i < fileList.length; i++) {

                    if (!fileList[i].isDirectory()) {

                        fileList[i].delete();

                    }

                }

            }

            returnVal = true;

        }

        catch (Exception e) {

            e.printStackTrace();

            returnVal = false;

        }

        return returnVal;

    }

    public static Vector getDirFiles(String dirPath) {

        Vector returnFiles = new Vector();

        try {

            File filesdir = new File(dirPath);

            if (filesdir.isDirectory()) {

                File[] farray = filesdir.listFiles();

                for (int i = 0; i < farray.length; i++) {

                    if (farray[i].isFile()) {

                        returnFiles.add(farray[i].getName());

                    }

                }

            }

        }

        catch (Exception ex) {

            ex.printStackTrace();

            returnFiles.clear();

        }

        return returnFiles;

    }

    /**
     * 取出文本文件的内容
     *
     * @param fileName 指定的文本文件的路径
     * @throws Exception 抛出文档输入输出异常
     * @return 返回文本文件的内容
     */
    public static String getTextFileContent(String fileName, String encoding) throws Exception {
        byte[] dataBytes = getFileBytes(fileName);
        return toString(dataBytes, encoding);
    }

    /**
     * 读取文件,变成Byte数组
     *
     * @param fileName 指定的文本文件的路径
     * @throws IOException 抛出文档输入输出异常
     * @return 返回文件的bytes数组
     */
    public static byte[] getFileBytes(String fileName) throws IOException {
        return getFileBytes(fileName, 0);
    }

    /**
     * 将字符串变成文件
     *
     * @param fileContent 指定要写成文件的内容
     * @param filename    要写成的文件名
     * @throws Exception 抛出文档输入输出异常
     */
    public static void str2file(String fileContent, String filename) throws Exception {
        if (fileContent == null || fileContent.equals("")) {
            return;
        }
        writeFile(filename, fileContent.getBytes());
    }

    /**
     * 带字符集的，检查是否可以生成指定字符集的--2010-2-18
     *
     * @param fileContent 要保存的字符串
     * @param filename    保存到哪个文件
     * @param encoding    字符集
     * @throws Exception
     */
    public static void str2file(String fileContent, String filename, String encoding) throws Exception {
        if (fileContent == null || fileContent.equals("")) {
            return;
        }
        writeFile(filename, fileContent.getBytes(encoding));
    }

    /**
     * 将本地文件转换为byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;

        try {
            File file = new File(filePath);

            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);

            byte[] b = new byte[1000];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);

            }

            fis.close();

            bos.close();

            buffer = bos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return buffer;

    }

    /*
     * public static void str2file2(String fileContent, String filename,String
     * encoding) throws Exception { if (fileContent == null ||
     * fileContent.equals("")) { return; } writeFile(filename,
     * fileContent.getBytes(encoding)); }
     */

    /**
     * 写文件
     *
     * @param filename 文件名
     * @param is       文件数据流
     * @throws IOException
     */
    public static void writeFile(String filename, InputStream is) throws IOException {
        writeFile(filename, getStreamByte(is, 0));
    }

    /**
     * 写文件
     *
     * @param filename  文件名
     * @param dataBytes 文件数据流
     * @throws IOException
     */
    public static void writeFile(String filename, byte[] dataBytes) throws IOException {
        writeFile(filename, dataBytes, 0, dataBytes.length);
    }

    /**
     * 写文件
     *
     * @param filename  文件名
     * @param dataBytes 文件数据流
     * @param start     开始位置
     * @param length    文件长度
     * @throws IOException
     */
    public synchronized static void writeFile(String filename, byte[] dataBytes, int start, int length)
            throws IOException {
        mkdirs(filename);
        File file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            file.delete();
        }
        FileOutputStream fileOut = null;
        fileOut = new FileOutputStream(filename);
        fileOut.write(dataBytes, start, length);
        fileOut.close();
    }

    // 复制文件
    private static void copyFile1(File sourceFile, File targetFile) throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);
        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);
        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
        // 关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // public static String createPageActionFile(String rowId,String
    // sDbService,HttpServletRequest req) throws Exception

    // public String createDBColumn(String rowId,String sDbService) throws Exception

    /**
     *
     * @param srcPath  源目录
     * @param destPath 目标目录
     * @throws IOException
     */
    public static void copyDir(String srcPath, String destPath) throws IOException {
        // 创建目标文件夹

        (new File(destPath)).mkdirs();
        //// 获取源文件夹当前下的文件或目录
        File[] file = (new File(srcPath)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {

                copyFile1(file[i], new File(destPath + File.separator + file[i].getName()));
            }
            if (file[i].isDirectory()) {
                // 复制目录
                String sourceDir = srcPath + File.separator + file[i].getName();
                String targetDir = destPath + File.separator + file[i].getName();


                copyDir1(sourceDir, targetDir);
            }
        }
    }

    // 复制文件夹--经比较，这个应该个copyDir的方法是一样的。
    private static void copyDir1(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录-
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile1(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + File.separator + file[i].getName();//"/"
                // 准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();//"/"
                copyDir1(dir1, dir2);
            }
        }
    }

    public static void delFolder(String filePath) throws IOException {
        File f = new File(filePath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {
            // 判断是文件还是目录
            if (f.listFiles().length == 0) {
                // 若目录下没有文件则直接删除
                f.delete();
            } else {
                // 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        delFolder(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        }

    }

    /**
     * 获取某个目录下所有文件的总大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getDirSize(File file) throws Exception {
        // 判断文件是否存在
        if (file.exists()) {
            // 如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {
                // 如果是文件则直接返回其大小,以“兆”为单位
                // double size = (double) file.length() / 1024 / 1024;
                long size = file.length();
                return size;
            }
        } else {
            throw new Exception("文件或者文件夹不存在，请检查路径是否正确！");
            // return 0.0;
        }
    }

    // public static List<String> saveUploadFile

    // public static List<String> saveFieldUploadFileLimit

    // public static List<String> saveFieldUploadFile

    public static void batDelFile(String path, String ext, boolean isDelEmptyDir) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files && files.length > 0) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        batDelFile(file2.getAbsolutePath(), ext, isDelEmptyDir);
                    } else {
                        //System.out.print("文件:");
                        //System.out.println(file2.getAbsolutePath());
                        if (file2.getAbsolutePath().endsWith(ext)) {
                            //System.out.print("删除文件:" + file2.getAbsolutePath());
                            boolean bool = file2.delete();
                            if (bool) {
                                //System.out.println("成功");
                            } else {
                                //System.out.println("失败!");
                            }
                        }
                    }
                }
            } else {
                //System.out.print("目录或文件:");
                //System.out.println(file.getAbsolutePath());

                if (isDelEmptyDir && file.isDirectory()) {
                    //System.out.print("删除空目录：" + file.getAbsolutePath());
                    boolean bool = file.delete();
                    if (bool) {
                        //System.out.println("成功");
                    } else {
                        //System.out.println("失败!");
                    }
                }

            }
        } else {
            //System.out.println("文件不存在");

        }
    }


    public static String downloadFile(String serverUrl, String savePath, int timeoutSeconds, String zipSavePath)
            throws Exception {
        String result;
        File f = new File(savePath); // 路径
        if (!f.exists()) {
            if (!f.mkdirs()) {
                throw new Exception("makdirs: '" + savePath + "'fail");
            }
        }
        URL url = new URL(serverUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(timeoutSeconds * 1000);
        // 防止屏蔽程序抓取而放回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
        Long totalSize = Long.parseLong(conn.getHeaderField("Content-Length"));
        if (totalSize > 0) {
            FileUtils.copyURLToFile(url, new File(zipSavePath));// 路径+文件
            result = "success";
        } else {
            throw new Exception("can not find serverUrl :{}" + serverUrl);
        }
        return result;
    }

    /**
     * 遍历某个目录及子目录下含某段文字的文件列表
     *
     * @param rootFolder
     * @param extName
     * @param searchContent
     * @return
     */
    public static List searchFileContent(String rootFolder, String extName, String searchContent) {
        List list = new ArrayList();
        File file = new File(rootFolder);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        List tmpList = FileUtil.searchFileContent(file2.getAbsolutePath(), extName, searchContent);

                        if (tmpList != null && tmpList.size() > 0) {

                            list.addAll(tmpList);

                        }
                    } else {
                        //logger.info("文件..............");
                        try {
                            String fileName = file2.getName();
                            //logger.info("文件名:" + fileName);
                            if (fileName != null && fileName.indexOf("." + extName) > -1) {
                                //logger.info("匹配.........");
                                String content = FileUtil.getTextFileContent(file2.getAbsolutePath(), "utf-8");
                                if (content.indexOf(searchContent) > -1) {
                                    list.add(file2.getAbsoluteFile());
                                }
                            } else {
                                //logger.info("不匹配........");
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            //System.out.println("文件不存在!");
        }
        return list;

    }

    /////////////////////////////////////

    /**
     * 读取文件,变成Byte数组
     *
     * @param fileName 指定的文本文件的路径
     * @param length   指定取的文件字节长度
     * @throws IOException 抛出文档输入输出异常
     * @return 返回文件的bytes数组
     */
    public static byte[] getFileBytes(String fileName, int length) throws IOException {
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
     * 将视频文件转换为m3u8
     *
     * @param sourceFile
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String convertM3u8(String sourceFile) throws IOException, InterruptedException {
        logger.info("传入的文件名:");
        logger.info(sourceFile);
        // asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv
        // 其他视频格式都可试验
        //// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        String newFileName = "";
        String relNewPath = "";

        if (sourceFile.toLowerCase().endsWith(".mp4") || sourceFile.toLowerCase().endsWith(".mpeg")) {
            String fullPath = sourceFile.replace("\\", "/").replace("//", "/");
            String path = fullPath.substring(0, fullPath.lastIndexOf("/") + 1);
            logger.info("path:::::");
            logger.info(path);
            String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1);
            logger.info(fileName);
            // logger.info("sourceExt：");
            // logger.info(message);
            String fileName1 = fileName.substring(0, fileName.lastIndexOf("."));
            logger.info("fileName1:::::");
            logger.info(fileName1);
            String newPath = path + fileName1;
            File file = new File(newPath);
            file.mkdirs();
            newFileName = path + fileName1 + "/" + fileName1 + ".m3u8";
            // logger.info("切片文件名:");
            // logger.info(newFileName);

            String cmd = "ffmpeg -i " + sourceFile
                    + " -hls_list_size 0 -hls_time 3 -c:v libx264 -c:a aac -strict -2 -f hls " + newFileName;
            logger.info(cmd);
            Process p = Runtime.getRuntime().exec(cmd);

            /*
             * while(p.isAlive()) { logger.info("切片运行中......"); Thread.sleep(1); }
             */

        }
        return newFileName;
    }

    /**
     * 单例模式的内部类
     *
     * @author user
     *
     */
    private static class ValidSerial {
        private static ValidSerial instance = null;

        public ValidSerial() {

        }

        public static ValidSerial getInstance() {
            if (instance == null) {
                instance = new ValidSerial();
            }
            return instance;

        }

        // public void check() throws Exception

    }
    // create a new file

    public static boolean newFile(String path, String name) {

        boolean returnVal = false;

        returnVal = mkdir(path);

        if (returnVal) {

            File f = new File(path, name);

            try {

                if (!f.exists()) {

                    returnVal = f.createNewFile();

                }

                else {

                    returnVal = true;

                }

            }

            catch (Exception e) {

                e.printStackTrace();

                returnVal = false;

            }

        }

        return returnVal;

    }

    public static void createFolders(String path, String split) {
        StringTokenizer st = new StringTokenizer(path, split);
        String path1 = st.nextToken() + split;
        String path2 = path1;
        while (st.hasMoreTokens()) {
            path1 = st.nextToken() + split;
            path2 += path1;
            File inbox = new File(path2);
            if (!inbox.exists())
                inbox.mkdir();
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
    public synchronized static byte[] getStreamByte(InputStream is, int length) throws IOException {
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
                        System.arraycopy(midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead,
                                bytesRead);
                    } else {
                        sizeCheck = totalBytesRead - length;
                        System.arraycopy(midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead,
                                bytesRead - sizeCheck);
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
                    System.arraycopy(midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead,
                            bytesRead);
                } else {
                    length += 2048 * 1024;
                    byte[] tmpBytes = allBytes;
                    allBytes = new byte[length];
                    System.arraycopy(tmpBytes, 0, allBytes, 0, tmpBytes.length);
                    System.arraycopy(midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead,
                            bytesRead - sizeCheck);
                }
            }
        }
        in.close();
        return midBytes(allBytes, 0, totalBytesRead);
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
            String dirPath = filename.substring(0, filename.lastIndexOf("/") + 1);
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
     * 将字节数组转换成有效字符
     *
     * @param src 字节数组
     * @return 有效字符串
     */
    public static String toString(byte[] src, String encoding) {
        /*
         * if (src == null || src.length == 0) { return ""; } String tmp = null; if
         * (src[0] < 0 && src.length == 1) { return ""; } if (src.length == 1) { return
         * new String(src); } if (src[0] < 0 && src[1] >= 0) { if (src[src.length - 1] >
         * 0 && src[src.length - 2] < 0) { return new String(src, 1, src.length - 2); }
         * else { return new String(src, 1, src.length - 1); } } else { if
         * (src[src.length - 1] > 0 && src[src.length - 2] < 0) { return new String(src,
         * 0, src.length - 1); } else { return new String(src, 0, src.length); } }
         */
        // 使用下面的方法，解决了生成JSP页面出现的乱码问题,但不知道对于Action类是否有影响。
        String s = "";
        try {
            if (encoding == null || encoding.trim().length() == 0) {
                s = new String(src);
            } else {
                s = new String(src, encoding);
            }
        } catch (Exception ex) {

        }
        return s;
    }

    // public static String saveWfFile(String fileContent, String filename,String
    // encoding,String dbService,HttpServletRequest request) throws Exception

    public static void main(String[] args) throws Exception {
        // 1 创建多层级目录
        System.out.println("开始演示.....");
        FileUtil.mkdir("d:\\demo1\\demo2\\demo3");
        // FileUtil.mkdirs("d:\\demo1\\demo2\\demo3");//等同于上面的，为兼容性考虑，不删除重复功能的方法。
        // 2 将文件从A目录复制到B目录，并更改文件名---目录结构已经创建，方法内部没有创建目录功能
        String sourceFile = "d:\\login.png";
        String destFile = "d:\\demo1\\demo2\\demo3\\login1.png";
        FileUtil.copy(sourceFile, destFile);

        // 3 将文件从旧目录复制到新目录，如果新目录不存在，则创建
        boolean bool = FileUtil.copy("d:\\", "login.png", "d:\\cccc\\dddd", "login3.png");
        if (bool) {
            System.out.println("复制成功");
        } else {
            System.out.println("复制失败 ");
        }
        // 4 将某个文件复制到新的目录下，并修改为新的文件名，如果目标目录不存在，则创建
        bool = FileUtil.copy("d:\\login.png", "d:\\cccc\\eeee", "login4.png");
        if (bool) {
            System.out.println("4复制成功");
        } else {
            System.out.println("4复制失败 ");
        }
        // 5 复制某个目录下的文件到新的目录，不复制目录下的子目录
        bool = false;
        bool = FileUtil.copyFiles("d:\\aaaa", "d:\\zzzz");
        // 6 删除某个目录
        bool = FileUtil.deleteFile("d:\\aaaa\\ccc.txt");// 删除一个文件
        bool = FileUtil.deleteFile("d:\\aaaa\\eeee");// 删除一个空目录--可以删除
        bool = FileUtil.deleteFile("d:\\aaaa\\ffff");// 删除一个非空目录 可以删除
        // 7 删除某个目录下的指定文件或目录
        bool = FileUtil.deleteFile("d:\\aaaa", "hhhh.txt");
        bool = FileUtil.deleteFile("d:\\aaaa", "gggg");// 非空目录-删除失败（不能删除非空目录）
        bool = FileUtil.deleteFile("d:\\aaaa", "mmmm");// 删除空目录---空目录可以删除
        // 8 递归删除目录，即目录下有文件和子目录都删除
        FileUtil.deleteDir("d:\\abcd");
        // 9删除目录下的文件，不删除子目录及子目录的文件
        FileUtil.deleteDirFiles("d:\\nnnn");

        // 10 获取某个目录下的文件列表，不含子目录及子目录下的文件
        Vector v = FileUtil.getDirFiles("d:\\aaaa");
        if (v != null) {
            for (Object s : v) {
                System.out.println(s);
            }
        }
        // 11 获取文本文件内容，指定字符集
        String content = FileUtil.getTextFileContent("d:\\UTF.txt", "utf-8");// 实测获取UTF8正常
        System.out.println(content);
        String content2 = FileUtil.getTextFileContent("d:\\GBK.txt", "GBK");// 实测获取GBK正常
        System.out.println(content2);
        // 12 将文件读到byte[]数组
        byte[] bytes = FileUtil.getFileBytes("d:\\GBK.txt");
        // 13 将字符串保存到文件中-未指定字符集
        FileUtil.str2file(content, "d:\\saveutf.txt");// 未指定字符集，实测是GBK
        FileUtil.str2file(content2, "d:\\savegbk.txt");// 未指定字符集，实测是GBK
        // 14 将字符串保存到文件中，指定字符集
        FileUtil.str2file(content, "d:\\saveutf2.txt", "UTF-8");
        FileUtil.str2file(content2, "d:\\savegbk2.txt", "GBK");
        FileUtil.str2file(content, "d:\\saveutf3.txt", "GBK");// 将utf串按GBK存储，不乱码
        FileUtil.str2file(content2, "d:\\savegbk3.txt", "UTF-8");// 将生成的GBK串按utf-8存储，不乱码,说明正确指定字符集就可以
        // 15 将文件读取到bytes数据
        byte[] fbytes = FileUtil.getBytes("d:\\GBK.txt");
        System.out.println(fbytes.length);
        // 16 读取文件流，写文件--读文件到流中再写入一个新的文件。
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("d:\\login.png");
            FileUtil.writeFile("d:\\login_copy.png", fis);
        } catch (Exception ex) {
        } finally {
            try {
                fis.close();
            } catch (Exception ex) {
            }
        }
        // 17 将bytes写入到文件
        byte[] imgbytes = FileUtil.getBytes("d:\\login.png");
        FileUtil.writeFile("d:\\login_copy2.png", imgbytes);

        //18 文件复制，参数为File
        File f1 = new File("d:\\login.png");
        File f2 = new File("d:\\login333.png");
        FileUtil.copyFile1(f1,f2);//文件复制
        //19目录完整复制
        FileUtil.copyDir("d:\\aaaa", "d:\\abcdefg");
        //copyDir1和copyDir是一样的。
        //FileUtil.copyDir1("d:\\aaaa", "d:\\abcdefg");
        //20 级联删除目录及目录下的文件
        System.out.println("delFolder......");
        FileUtil.delFolder("d:\\bcdef");//这个应该和 FileUtil.deleteDir("目录")  方法功能一样
        //21 获取目录下文件的总大小
        long size = FileUtil.getDirSize(new File("d:\\图片"));
        System.out.println("d:\\图片目录大小"+size);
        //22 删除某目录及所有子目录扩展名的文件
        FileUtil.batDelFile("d:\\aaaa", ".bak" ,true);//最后参数true/false ,为设置是否删除空目录
        //23 文件下载---使用了common-io的FileUtils
        //参数  文焕 URL 地址 、 本地目录、超时秒数、文件存储到本地的路径+文件名
        FileUtil.downloadFile("http://www.zzyicheng.cn/portal/site/home/img/trainnavList2.png","d:\\tmpimg",30, "d:\\tmpimg\\train.png");
        //24 遍历某个目录下 指定扩展名中含某字符串的文件列表
        System.out.println("检查含中的文件......");
        List flist = FileUtil.searchFileContent("d:\\aaaa", ".txt","中");
        for(Object s:flist)
        {
            System.out.println(s);

        }
        System.out.println("结束演示.....");



    }

}
