package org.openjweb.dev.util;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.openjweb.common.util.FileUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.dev.entity.TableColumnInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            str = t.render();
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
}
