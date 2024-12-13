package org.openjweb.dev.util;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.openjweb.common.util.FileUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommColumnDef;
import org.openjweb.core.entity.CommTableDef;
import org.openjweb.core.module.params.CommColumnDefParam;
import org.openjweb.core.module.params.CommTableDefParam;
import org.openjweb.core.service.CommColumnDefService;
import org.openjweb.core.service.CommTableDefService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DevUtilV2 {

    @Resource
    private CommTableDefService commTableDefService;

    @Resource
    private CommColumnDefService commColumnDefService;

    //设置Java模版文件路径
    @Value("${openjweb.dev.javaTemplatePath:}") String javaTemplatePath;

    //java主工程的路径
    @Value("${openjweb.dev.rootProjectPath:}") String rootProjectPath;

    //FLOKI TURBO puppies


    public void  createJavaSource(String tableName){
        log.info("查询的表结构名:"+tableName);
        CommTableDefParam param = new CommTableDefParam();
        param.setTableName(tableName);
        CommTableDef tableDef = null;
        List<CommTableDef> list = this.commTableDefService.queryList(param);
        GroupTemplate gt = null;
        Configuration cfg = null;
        Template t = null;


        if(list==null){
            log.info("没查到表结构........");
        }
        else{
            //tableDef = list.get
            log.info("查到表结构......"+String.valueOf(list.size()));

        }
        List<CommColumnDef> colList = null;
        List<Map<String,String>>  colMapList = new ArrayList<>();
        if(list!=null&&list.size()==1){
            tableDef = list.get(0);
            CommColumnDefParam commColumnDefParam = new CommColumnDefParam();
            commColumnDefParam.setTableSerialNo(tableDef.getSerialNo());
            log.info("表结构的serial_No:"+String.valueOf(tableDef.getSerialNo()));
            colList = this.commColumnDefService.queryList(commColumnDefParam);


            if(colList!=null&&colList.size()>0){
                log.info("列数量："+colList.size());
                for(CommColumnDef entity:colList){
                    String tableId = "";

                    if(entity.getColumnName().toLowerCase().equals("row_id")){
                        //默认ROWID为主键
                        tableId = "@TableId(type = IdType.ASSIGN_UUID)";
                    }
                    Map map = new HashMap();
                    map.put("tableId",tableId);

                    //生成ApiPerperty
                    String colDesc = entity.getColumnDesc();
                    String colField = StringUtil.getFieldNameByColName(entity.getColumnName());
                    map.put("apiProperty","@ApiModelProperty(value =\""+colDesc+"\" )");

                    //下面生成类属性定义
                    String colType = entity.getColumnDatatype().toLowerCase();
                    if(colType.indexOf("varchar")>-1||colType.indexOf("char")>-1
                            ||colType.indexOf("text")>-1||colType.indexOf("clob")>-1 ||colType.indexOf("string")>-1
                            ||colType.indexOf("time")>-1||colType.indexOf("str_")>-1){
                        colType = "String";

                    }
                    else if(colType.equals("number")||colType.endsWith("int")||colType.equals("integer")){
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
                    log.info("map中的fieldName::::"+colField);
                    map.put("fieldName",colField);//类属性名
                    String fieldNameUpper = colField.substring(0,1).toUpperCase()+colField.substring(1);
                    //log.info("fieldNameUpper:");
                    //log.info(fieldNameUpper);

                    map.put("fieldNameUpper",fieldNameUpper);//类属性名
                    map.put("columnName",entity.getColumnName());
                    map.put("fieldNameDesc",entity.getColumnDesc());
                    //对于设置默认值的字段
                    String defaultValueExpr = "";
                    if(entity.getColumnName().toLowerCase().equals("record_version")){
                        defaultValueExpr = "@Version";//乐观锁
                    }
                    else if(entity.getColumnName().toLowerCase().equals("create_dt")){
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT)";//
                    }
                    else if(entity.getColumnName().toLowerCase().equals("create_uid")){
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT)";//
                    }
                    else if(entity.getColumnName().toLowerCase().equals("update_dt")){
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT_UPDATE)";//
                    }
                    else if(entity.getColumnName().toLowerCase().equals("update_uid")){
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT_UPDATE)";//
                    }
                    map.put("defaultValueExpr",defaultValueExpr);
                    colMapList.add(map);
                }
            }
            else{
                log.info("没查到表字段数量.....");
            }
            String entityClassName = StringUtil.getEntityNameByTableName(tableName);

            log.info("表对应的类名:"+entityClassName);
            String tableDesc =  tableDef.getTableDesc()==null?"":tableDef.getTableDesc();

            //这里暂时硬编码设置下子系统对应的模块路径
            String modulePath = "";
            String packagePath = "";
            String packageName = "";//这个指的是实体类的包路径
            //根据表名转换为类名
            String basePackage = "";



            if(tableName.startsWith("comm_")||tableName.startsWith("sms_")){
                modulePath = "openjweb-core";
                packagePath = "org/openjweb/core/";

            }
            else if(tableName.startsWith("b2c_")){
                modulePath = "openjweb-b2b2c";
                packagePath = "org/openjweb/b2c/";
            }
            else if(tableName.startsWith("cms_")){
                modulePath = "openjweb-cms";
                packagePath = "org/openjweb/cms/";
            }
            else if(tableName.startsWith("sns_")){
                modulePath = "openjweb-sns";
                packagePath = "org/openjweb/sns/";
            }
            else if(tableName.startsWith("weixin_")){
                modulePath = "openjweb-weixin";
                packagePath = "org/openjweb/weixin/";
            }
            else{
                modulePath = "openjweb-core";
                packagePath = "org/openjweb/core/";
            }
            //packageName = packagePath.substring(0,packagePath.length()-1).replace("/","");
            packageName = packagePath.replace("/",".")+"entity";
            basePackage = packagePath.substring(0,packagePath.length()-1).replace("/",".");
            String fullEntityClassName = basePackage+".entity."+entityClassName;


            FileResourceLoader fileResourceLoader = new FileResourceLoader(javaTemplatePath,"utf-8");

            try {
                cfg = Configuration.defaultConfiguration();
                cfg.setCharset("utf-8");//设置文件的字符集
            } catch (IOException e) {
                e.printStackTrace();
            }
            //开始逐个生成，先生成实体类
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/EntityTemplate.java");
            t.binding("packageName",packageName);
            t.binding("tableDesc",tableDesc);
            t.binding("tableName",tableName);
            t.binding("entityClassName",entityClassName);
            t.binding("fieldList",colMapList);
            String str = null;
            try{
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            String saveFilePath = rootProjectPath+"/"+modulePath+"/src/main/java/"+packagePath+"entity/"+entityClassName+".java";
            //生成到文件
            //数据库表关联的包的路径
            try {
                FileUtil.str2file(str,saveFilePath,"utf-8");
            } catch (Exception e) {

                e.printStackTrace();
            }
            log.info("开始生成参数类......");

            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/ParamTemplate.java");
            t.binding("basePackage",basePackage);
            t.binding("fullEntityClassName",fullEntityClassName);
            t.binding("entityClassName",entityClassName);
            str = "";
            try{
                str = t.render();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            //log.info("生成的文件内容:"+str);
            //生成到文件
            //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";
            String paramFileName =  rootProjectPath+"/"+modulePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/module/params/"+entityClassName+"Param.java";
            try {
                FileUtil.str2file(str,paramFileName,"utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("生成Mapper类......");
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/MapperTemplate.java");
            t.binding("basePackage",basePackage);
            t.binding("fullEntityClassName",fullEntityClassName);
            t.binding("entityClassName",entityClassName);
            t.binding("tableName",tableName);
            str = null;
            try{
                str = t.render();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            log.info("生成的文件内容:"+str);
            //生成到文件
            //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";
            paramFileName = rootProjectPath+"/"+modulePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/mapper/"+entityClassName+"Mapper.java";
            try {
                FileUtil.str2file(str,paramFileName,"utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ////////////////////////
            log.info("生成Mapping.xml...........");

            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/MapperTemplate.xml");

            //截取basePackage
            t.binding("basePackage",packageName.replace(".entity",""));
            t.binding("fullClassName",packageName+"."+entityClassName);
            t.binding("packageName",packageName);
            t.binding("tableDesc",tableDesc);
            t.binding("tableName",tableName);
            t.binding("entityClassName",entityClassName);
            t.binding("fieldList",colMapList);
            str = null;
            try{
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            paramFileName = rootProjectPath+"/"+modulePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/mapper/mapping/"+entityClassName+"Mapper.xml";

            try {
                FileUtil.str2file(str,paramFileName,"utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("开始生成Service类..............");
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/ServiceTemplate.java");
            //截取basePackage
            t.binding("basePackage",packageName.replace(".entity",""));
            t.binding("fullClassName",packageName+"."+entityClassName);
            t.binding("packageName",packageName);
            t.binding("classNameLower",entityClassName.substring(0,1).toLowerCase()+entityClassName.substring(1));
            t.binding("tableDesc",tableDesc);
            t.binding("tableName",tableName);
            t.binding("entityClassName",entityClassName);
            t.binding("fieldList",colMapList);
            str = null;
            try{
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            paramFileName = rootProjectPath+"/"+modulePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/service/"+entityClassName+"Service.java";
            try {
                FileUtil.str2file(str,paramFileName,"utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("生成控制层类...........");
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/ControllerTemplate.java");
            t.binding("basePackage",packageName.replace(".entity",""));
            t.binding("fullClassName",packageName+"."+entityClassName);
            t.binding("packageName",packageName);
            t.binding("classNameLower",entityClassName.substring(0,1).toLowerCase()+entityClassName.substring(1));
            t.binding("tableDesc",tableDesc);
            t.binding("tableName",tableName);
            t.binding("entityClassName",entityClassName);
            t.binding("fieldList",colMapList);
            str = null;
            try{
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            }
            catch(Exception ex){
                ex.printStackTrace();

            }
            paramFileName = rootProjectPath+"/"+modulePath+"/"+"src/main/java/"+basePackage.replace(".","/")+"/api/"+entityClassName+"Api.java";
            try {
                FileUtil.str2file(str,paramFileName,"utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
