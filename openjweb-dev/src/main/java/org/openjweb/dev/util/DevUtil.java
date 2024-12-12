package org.openjweb.dev.util;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.openjweb.common.util.FileUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.config.BeetlConf;
import org.openjweb.dev.entity.TableColumnInfo;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DevUtil {

    public static List<Map<String,String>>  getEntityVar(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                          String packageName) throws SQLException {


        List<TableColumnInfo> list = DbUtil.getTableColumnInfo(dbDriver,url,schecma,username,password,tableName);
        List<Map<String,String>> resultList = new ArrayList<>();
        if(list!=null&&list.size()>0){
            for(TableColumnInfo columnInfo:list){
                //String
                String tableId = "";
                log.info("当前字段::::::");
                log.info(columnInfo.getColumnName());
                if(columnInfo.getColumnName().toLowerCase().equals("row_id")){
                    //默认ROWID为主键
                    tableId = "@TableId(type = IdType.ASSIGN_UUID)";
                }
                Map map = new HashMap();
                map.put("tableId",tableId);

                //生成ApiPerperty
                String colDesc = columnInfo.getColumnDesc();
                String colField = StringUtil.getFieldNameByColName(columnInfo.getColumnName());
                map.put("apiProperty","@ApiModelProperty(value =\""+colDesc+"\" )");

                //下面生成类属性定义
                String colType = columnInfo.getColumnType().toLowerCase();
                if(colType.indexOf("varchar")>-1||colType.indexOf("char")>-1
                        ||colType.indexOf("text")>-1||colType.indexOf("clob")>-1  ){
                    colType = "String";

                }
                else if(colType.equals("number")||colType.endsWith("int")){
                    colType = "Long";//整数类型
                }
                else if(colType.equals("decimal")||colType.equals("numeric")){
                    colType = "Double";
                }
                String fieldDeclare = "private "+colType+" "+colField;
                if(colField.equals("record_version")){
                    log.info("乐观锁字段..........");
                    fieldDeclare   += " = 0L";//乐观锁字段设置默认值
                }
                fieldDeclare += ";";
                map.put("fieldDeclare",fieldDeclare);
                map.put("fieldName",colField);//类属性名
                String fieldNameUpper = colField.substring(0,1).toUpperCase()+colField.substring(1);
                log.info("fieldNameUpper:");
                log.info(fieldNameUpper);

                map.put("fieldNameUpper",fieldNameUpper);//类属性名
                map.put("columnName",columnInfo.getColumnName());
                map.put("fieldNameDesc",columnInfo.getColumnDesc());
                //对于设置默认值的字段
                String defaultValueExpr = "";
                if(columnInfo.getColumnName().toLowerCase().equals("record_version")){
                    defaultValueExpr = "@Version";//乐观锁
                }
                else if(columnInfo.getColumnName().toLowerCase().equals("create_dt")){
                    defaultValueExpr = "@TableField(fill = FieldFill.INSERT)";//
                }
                else if(columnInfo.getColumnName().toLowerCase().equals("create_uid")){
                    defaultValueExpr = "@TableField(fill = FieldFill.INSERT)";//
                }
                else if(columnInfo.getColumnName().toLowerCase().equals("update_dt")){
                    defaultValueExpr = "@TableField(fill = FieldFill.INSERT_UPDATE)";//
                }
                else if(columnInfo.getColumnName().toLowerCase().equals("update_uid")){
                    defaultValueExpr = "@TableField(fill = FieldFill.INSERT_UPDATE)";//
                }
                map.put("defaultValueExpr",defaultValueExpr);

                resultList.add(map);
            }
        }
        return resultList;
    }
    public static String createEntityFile(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                          String packageName,String saveFilePath) throws SQLException {
        List<Map<String,String>> list = DevUtil.getEntityVar(dbDriver,url,schecma,  username,  password, tableName,
                  packageName) ;
        ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates","utf-8");
        //ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates");

        GroupTemplate gt = null;
        boolean bool = true;//使用文件路径，改为false则使用resource/templates路径
        Configuration cfg = null;
        try {

            cfg = Configuration.defaultConfiguration();
            cfg.setCharset("utf-8");//设置文件的字符集
            //cfg.set

        } catch (IOException e) {
            e.printStackTrace();
        }
        gt = new GroupTemplate(cpLoader, cfg);

        Template t = gt.getTemplate("dev/EntityTemplate.java");
        //根据表名转换为类名
        String entityClassName = StringUtil.getEntityNameByTableName(tableName);

        log.info("表对应的类名:"+entityClassName);
        String tableDesc =  DbUtil.getTableComment(dbDriver,url,schecma,username,password,tableName);
        log.info("表说明:");
        log.info(tableDesc);
        log.info("entityClassName::::");
        log.info(entityClassName);



        t.binding("packageName",packageName);
        t.binding("tableDesc",tableDesc);
        t.binding("tableName",tableName);
        t.binding("entityClassName",entityClassName);
        t.binding("fieldList",list);
        String str = null;
        try{

            str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？

        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }

        log.info("生成的文件内容:"+str);
        //生成到文件
        //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";

        try {
            //FileUtil.str2file(str,saveFilePath,"utf-8");
            try {
                File outputFile = new File(saveFilePath);
                FileWriter writer = new FileWriter(outputFile);
                writer.write(str );
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }


        return "";

    }

    public static String createParamFile(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                          String basePackage,String projectBasePath) throws SQLException {

        ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates","utf-8");
        GroupTemplate gt = null;
        boolean bool = true;//使用文件路径，改为false则使用resource/templates路径
        Configuration cfg = null;
        try {

            cfg = Configuration.defaultConfiguration();
            cfg.setCharset("utf-8");//设置文件的字符集

        } catch (IOException e) {
            e.printStackTrace();
        }
        gt = new GroupTemplate(cpLoader, cfg);

        Template t = gt.getTemplate("dev/ParamTemplate.java");
        //根据表名转换为类名
        String entityClassName = StringUtil.getEntityNameByTableName(tableName);

        log.info("表对应的类名:"+entityClassName);
        String tableDesc =  DbUtil.getTableComment(dbDriver,url,schecma,username,password,tableName);
        log.info("表说明:");
        log.info(tableDesc);
        log.info("entityClassName::::");
        log.info(entityClassName);
        String fullEntityClassName = basePackage+".entity."+entityClassName;

        t.binding("basePackage",basePackage);
        t.binding("fullEntityClassName",fullEntityClassName);

        t.binding("entityClassName",entityClassName);

        String str = null;
        try{
            str = t.render();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }
        log.info("生成的文件内容:"+str);
        //生成到文件
        //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";
        String paramFileName = projectBasePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/module/params/"+entityClassName+"Param.java";
        log.info("参数类位置：：：：");
        log.info(paramFileName);

        try {
            FileUtil.str2file(str,paramFileName,"utf-8");
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }


        return "";

    }

    public static String createMapperFile(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                         String basePackage,String projectBasePath) throws SQLException {

        ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates","utf-8");
        GroupTemplate gt = null;
        boolean bool = true;//使用文件路径，改为false则使用resource/templates路径
        Configuration cfg = null;
        try {

            cfg = Configuration.defaultConfiguration();
            log.info("默认字符集："+cfg.getCharset());

            cfg.setCharset("utf-8");//设置文件的字符集
            //log.info(cfg.getCharset());


        } catch (IOException e) {
            e.printStackTrace();
        }
        gt = new GroupTemplate(cpLoader, cfg);

        Template t = gt.getTemplate("dev/MapperTemplate.java",cpLoader);
        //根据表名转换为类名
        String entityClassName = StringUtil.getEntityNameByTableName(tableName);

        log.info("表对应的类名:"+entityClassName);
        String tableDesc =  DbUtil.getTableComment(dbDriver,url,schecma,username,password,tableName);
        log.info("表说明:");
        log.info(tableDesc);
        log.info("entityClassName::::");
        log.info(entityClassName);
        String fullEntityClassName = basePackage+".entity."+entityClassName;

        t.binding("basePackage",basePackage);
        t.binding("fullEntityClassName",fullEntityClassName);

        t.binding("entityClassName",entityClassName);
        t.binding("tableName",tableName);

        String str = null;
        try{
            str = t.render();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }
        log.info("生成的文件内容:"+str);
        //生成到文件
        //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";
        String paramFileName = projectBasePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/mapper/"+entityClassName+"Mapper.java";
        log.info("参数类位置：：：：");
        log.info(paramFileName);

        try {
            FileUtil.str2file(str,paramFileName,"utf-8");
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }


        return "";

    }

    public static String createXmlFile(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                          String packageName,String saveFilePath) throws SQLException {
        List<Map<String,String>> list = DevUtil.getEntityVar(dbDriver,url,schecma,  username,  password, tableName,
                packageName) ;
        ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates","utf-8");
        //ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates");

        GroupTemplate gt = null;
        boolean bool = true;//使用文件路径，改为false则使用resource/templates路径
        Configuration cfg = null;
        try {

            cfg = Configuration.defaultConfiguration();
            cfg.setCharset("utf-8");//设置文件的字符集
            //cfg.set

        } catch (IOException e) {
            e.printStackTrace();
        }
        gt = new GroupTemplate(cpLoader, cfg);

        Template t = gt.getTemplate("dev/MapperTemplate.xml");
        //根据表名转换为类名
        String entityClassName = StringUtil.getEntityNameByTableName(tableName);

        log.info("表对应的类名:"+entityClassName);
        String tableDesc =  DbUtil.getTableComment(dbDriver,url,schecma,username,password,tableName);
        log.info("表说明:");
        log.info(tableDesc);
        log.info("entityClassName::::");
        log.info(entityClassName);
        //截取basePackage
        t.binding("basePackage",packageName.replace(".entity",""));

        t.binding("fullClassName",packageName+"."+entityClassName);


        t.binding("packageName",packageName);
        t.binding("tableDesc",tableDesc);
        t.binding("tableName",tableName);
        t.binding("entityClassName",entityClassName);
        t.binding("fieldList",list);
        String str = null;
        try{

            str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？

        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }

        log.info("生成的文件内容:"+str);
        //生成到文件
        //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";

        try {
            FileUtil.str2file(str,saveFilePath,"utf-8");
            /* 这个不一定支持层级目录自动创建
            try {
                File outputFile = new File(saveFilePath);
                FileWriter writer = new FileWriter(outputFile);
                writer.write(str );
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }


        return "";

    }

    public static String createServiceFile(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                       String packageName,String saveFilePath) throws SQLException {
        List<Map<String,String>> list = DevUtil.getEntityVar(dbDriver,url,schecma,  username,  password, tableName,
                packageName) ;
        ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates","utf-8");
        //ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates");

        GroupTemplate gt = null;
        boolean bool = true;//使用文件路径，改为false则使用resource/templates路径
        Configuration cfg = null;
        try {

            cfg = Configuration.defaultConfiguration();
            cfg.setCharset("utf-8");//设置文件的字符集
            //cfg.set

        } catch (IOException e) {
            e.printStackTrace();
        }
        gt = new GroupTemplate(cpLoader, cfg);

        Template t = gt.getTemplate("dev/ServiceTemplate.java");
        //根据表名转换为类名
        String entityClassName = StringUtil.getEntityNameByTableName(tableName);

        log.info("表对应的类名:"+entityClassName);
        String tableDesc =  DbUtil.getTableComment(dbDriver,url,schecma,username,password,tableName);
        log.info("表说明:");
        log.info(tableDesc);
        log.info("entityClassName::::");
        log.info(entityClassName);
        //截取basePackage
        t.binding("basePackage",packageName.replace(".entity",""));

        t.binding("fullClassName",packageName+"."+entityClassName);


        t.binding("packageName",packageName);
        t.binding("classNameLower",entityClassName.substring(0,1).toLowerCase()+entityClassName.substring(1));
        t.binding("tableDesc",tableDesc);
        t.binding("tableName",tableName);
        t.binding("entityClassName",entityClassName);
        t.binding("fieldList",list);
        String str = null;
        try{

            str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？

        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }

        log.info("生成的文件内容:"+str);
        //生成到文件
        //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";

        try {
            FileUtil.str2file(str,saveFilePath,"utf-8");
            /*try {
                File outputFile = new File(saveFilePath);
                FileWriter writer = new FileWriter(outputFile);
                writer.write(str );
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }


        return "";

    }

    public static String createApiFile(String dbDriver,String url,String schecma,String username,String password,String tableName,
                                           String packageName,String saveFilePath) throws SQLException {
        log.info("生成Controller..............");

        List<Map<String,String>> list = DevUtil.getEntityVar(dbDriver,url,schecma,  username,  password, tableName,
                packageName) ;
        ClasspathResourceLoader cpLoader = new ClasspathResourceLoader( "templates","utf-8");
        cpLoader.setCharset("UTF-8");
        //ClasspathResourceLoader cpLoader = new ClasspathResourceLoader("templates");

        GroupTemplate gt = null;
        boolean bool = true;//使用文件路径，改为false则使用resource/templates路径
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
            cfg.setCharset("utf-8");//设置文件的字符集
        } catch (IOException e) {
            e.printStackTrace();
        }
        //FileResourceLoader fileResourceLoader = new FileResourceLoader("D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\","utf-8");
        gt = new GroupTemplate(cpLoader, cfg);
        //gt = new GroupTemplate(fileResourceLoader, cfg);
        Template t = gt.getTemplate("dev/ControllerTemplate.java");
        //根据表名转换为类名
        String entityClassName = StringUtil.getEntityNameByTableName(tableName);

        log.info("表对应的类名:"+entityClassName);
        String tableDesc =  DbUtil.getTableComment(dbDriver,url,schecma,username,password,tableName);
        log.info("表说明:");
        log.info(tableDesc);
        log.info("entityClassName::::");
        log.info(entityClassName);
        //截取basePackage
        t.binding("basePackage",packageName.replace(".entity",""));

        t.binding("fullClassName",packageName+"."+entityClassName);


        t.binding("packageName",packageName);
        t.binding("classNameLower",entityClassName.substring(0,1).toLowerCase()+entityClassName.substring(1));
        t.binding("tableDesc",tableDesc);
        t.binding("tableName",tableName);
        t.binding("entityClassName",entityClassName);
        t.binding("fieldList",list);
        String str = null;
        try{
           /* try (FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath)) {
                t.renderTo(fileOutputStream);
            }
            catch(Exception ex){}*/
          str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }

        log.info("生成的文件内容:"+str);
        //生成到文件
        //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";

        try {
            FileUtil.str2file(str,saveFilePath,"utf-8");
            /*try {
                File outputFile = new File(saveFilePath);
                FileWriter writer = new FileWriter(outputFile);
                writer.write(str );
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }


        return "";

    }

}
