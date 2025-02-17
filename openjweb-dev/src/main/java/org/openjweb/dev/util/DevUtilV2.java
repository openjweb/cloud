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
    @Value("${openjweb.dev.javaTemplatePath:}")
    String javaTemplatePath;

    //java主工程的路径
    @Value("${openjweb.dev.rootProjectPath:}")
    String rootProjectPath;

    //VUE前端页面路径
    @Value("${openjweb.dev.vuePath:}")
    String vuePath;

    //是否生成VUE页面
    @Value("${openjweb.dev.isCreateVue:}")
    boolean isCreateVue;


    //FLOKI TURBO puppies


    public void createJavaSource(String tableName) {
        log.info("查询的表结构名:" + tableName);
        CommTableDefParam param = new CommTableDefParam();
        param.setTableName(tableName);
        CommTableDef tableDef = null;
        List<CommTableDef> list = this.commTableDefService.queryList(param);
        GroupTemplate gt = null;
        Configuration cfg = null;
        Template t = null;


        if (list == null) {
            log.info("没查到表结构........");
        } else {
            //tableDef = list.get
            log.info("查到表结构......" + String.valueOf(list.size()));

        }
        List<CommColumnDef> colList = null;
        List<Map<String, String>> colMapList = new ArrayList<>();
        if (list != null && list.size() == 1) {
            tableDef = list.get(0);
            CommColumnDefParam commColumnDefParam = new CommColumnDefParam();
            commColumnDefParam.setTableSerialNo(tableDef.getSerialNo());
            log.info("表结构的serial_No:" + String.valueOf(tableDef.getSerialNo()));
            colList = this.commColumnDefService.queryList(commColumnDefParam);


            if (colList != null && colList.size() > 0) {
                log.info("列数量：" + colList.size());
                for (CommColumnDef entity : colList) {
                    String tableId = "";

                    if (entity.getColumnName().toLowerCase().equals("row_id")) {
                        //默认ROWID为主键
                        tableId = "@TableId(type = IdType.ASSIGN_UUID)";
                    }
                    Map map = new HashMap();
                    map.put("tableId", tableId);

                    //生成ApiPerperty
                    String colDesc = entity.getColumnDesc();
                    String colField = StringUtil.getFieldNameByColName(entity.getColumnName());
                    map.put("apiProperty", "@ApiModelProperty(value =\"" + colDesc + "\" )");

                    //下面生成类属性定义
                    //log.info("当前列：");
                    //log.info(entity.getColumnName());
                    //如果没设置字段类型，暂按照字符串处理
                    String colType = entity.getColumnDatatype()==null?"varchar":entity.getColumnDatatype().toLowerCase();
                    //String colType = entity.getColumnDatatype().toLowerCase();

                    if (colType.indexOf("varchar") > -1 || colType.indexOf("char") > -1
                            || colType.indexOf("text") > -1 || colType.indexOf("clob") > -1 || colType.indexOf("string") > -1
                            || colType.indexOf("time") > -1 || colType.indexOf("str_") > -1) {
                        colType = "String";

                    } else if (colType.equals("number") || colType.endsWith("int") || colType.equals("integer")) {
                        colType = "Long";//整数类型
                    } else if (colType.equals("decimal") || colType.equals("numeric")) {
                        colType = "Double";
                    }
                    String fieldDeclare = "private " + colType + " " + colField;
                    if (colField.equals("record_version")) {
                        log.info("乐观锁字段..........");
                        fieldDeclare += " = 0L";//乐观锁字段设置默认值
                    }
                    fieldDeclare += ";";
                    map.put("fieldDeclare", fieldDeclare);
                    log.info("map中的fieldName::::" + colField);
                    map.put("fieldName", colField);//类属性名
                    String fieldNameUpper = colField.substring(0, 1).toUpperCase() + colField.substring(1);
                    //log.info("fieldNameUpper:");
                    //log.info(fieldNameUpper);

                    map.put("fieldNameUpper", fieldNameUpper);//类属性名
                    map.put("columnName", entity.getColumnName());
                    map.put("fieldNameDesc", entity.getColumnDesc());
                    //对于设置默认值的字段
                    String defaultValueExpr = "";
                    if (entity.getColumnName().toLowerCase().equals("record_version")) {
                        defaultValueExpr = "@Version";//乐观锁
                    } else if (entity.getColumnName().toLowerCase().equals("create_dt")) {
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT)";//
                    } else if (entity.getColumnName().toLowerCase().equals("create_uid")) {
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT)";//
                    } else if (entity.getColumnName().toLowerCase().equals("update_dt")) {
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT_UPDATE)";//
                    } else if (entity.getColumnName().toLowerCase().equals("update_uid")) {
                        defaultValueExpr = "@TableField(fill = FieldFill.INSERT_UPDATE)";//
                    }
                    map.put("defaultValueExpr", defaultValueExpr);
                    //查询哪些是查询条件列
                    String queryFieldExpr = "";
                    String queryInputType ="";
                    if("Y".equals(entity.getIsQueryCol())){
                        //如果是查询条件字段---为字段生成VUE查询条件，查询条件应根据组件的类型，设置成输入框还是下拉还是日期组件
                        String inputType = entity.getInputType()==null?"":entity.getInputType();
                        switch (inputType){
                            case "INPUT_TEXTAREA"://多行文本框的组件一般不会作为查询条件,查询条件按照输入框处理
                                queryFieldExpr = "<el-form-item label=\""+entity.getColumnNameCn()+"\">\r\n" +
                                        "            <el-input\r\n" +
                                        "              v-model=\"queryForm"+entity.getClsFieldName()+"\"\r\n" +
                                        "              placeholder=\"请输入"+entity.getColumnNameCn()+"\"\r\n" +
                                        "              clearable\r\n" +
                                        "            />\n\n" +
                                        "          </el-form-item>";

                                break;
                            case "INPUT_DATE"://日期型--后续再完善
                                queryFieldExpr = "<el-form-item label=\""+entity.getColumnNameCn()+"\">\r\n" +
                                        "            <el-input\r\n" +
                                        "              v-model=\"queryForm"+entity.getClsFieldName()+"\"\r\n" +
                                        "              placeholder=\"请输入"+entity.getColumnNameCn()+"\"\r\n" +
                                        "              clearable\r\n" +
                                        "            />\r\n" +
                                        "          </el-form-item>";

                                break;
                            default:
                                queryFieldExpr = "<el-form-item label=\""+entity.getColumnNameCn()+"\">\r\n" +
                                        "            <el-input\r\n" +
                                        "              v-model=\"queryForm"+entity.getClsFieldName()+"\"\r\n" +
                                        "              placeholder=\"请输入"+entity.getColumnNameCn()+"\"\r\n" +
                                        "              clearable\r\n" +
                                        "            />\r\n" +
                                        "          </el-form-item>\r\n";
                        }
                        map.put("vueQueryListItem",queryFieldExpr);
                    }
                    else{
                        map.put("vueQueryListItem","");
                    }

                    colMapList.add(map);
                    //下面生成列表界面的列表字段
                    if("Y".equals(entity.getIsListCol())){
                        //如果在列表界面显示
                        String listColExpr = "<el-table-column\r\n" +
                                "          show-overflow-tooltip\r\n" +
                                "          prop=\""+entity.getClsFieldName()+"\"\r\n" +
                                "          label=\""+entity.getColumnNameCn()+"\"\r\n" +
                                "          sortable\r\n" +
                                "      ></el-table-column>\r\n";

                        map.put("vueTableListItem",listColExpr);//在列表页面显示

                    }

                }
            } else {
                log.info("没查到表字段数量.....");
            }
            String entityClassName = StringUtil.getEntityNameByTableName(tableName);
            String classNameLower = entityClassName.substring(0, 1).toLowerCase() + entityClassName.substring(1);
            //新标准，将/demo/api/${classNameLower}改为api/subsysCode/xxx
            String subSysCode = tableName.substring(0, tableName.indexOf("_"));
            String replaceApi = "api/" + subSysCode + "/" + StringUtil.getFieldNameByColName(tableName.substring(tableName.indexOf("_") + 1));
            log.info("替换的API接口URL路径:::::::::");
            log.info(replaceApi);

            log.info("表对应的类名:" + entityClassName);
            String tableDesc = tableDef.getTableDesc() == null ? "" : tableDef.getTableDesc();

            //这里暂时硬编码设置下子系统对应的模块路径
            String modulePath = "";
            String packagePath = "";
            String packageName = "";//这个指的是实体类的包路径
            //根据表名转换为类名
            String basePackage = "";


            if (tableName.startsWith("comm_") || tableName.startsWith("sms_")) {
                modulePath = "openjweb-core";
                packagePath = "org/openjweb/core/";

            } else if (tableName.startsWith("b2c_")) {
                modulePath = "openjweb-b2b2c";
                packagePath = "org/openjweb/b2c/";
            } else if (tableName.startsWith("cms_")||tableName.startsWith("portal_")) {
                //门户和CMS放在openjweb-cms里
                modulePath = "openjweb-cms";
                packagePath = "org/openjweb/cms/";
            } else if (tableName.startsWith("sns_")) {
                modulePath = "openjweb-sns";
                packagePath = "org/openjweb/sns/";
            } else if (tableName.startsWith("weixin_")) {
                modulePath = "openjweb-weixin";
                packagePath = "org/openjweb/weixin/";
            } else {
                modulePath = "openjweb-core";
                packagePath = "org/openjweb/core/";
            }
            //packageName = packagePath.substring(0,packagePath.length()-1).replace("/","");
            packageName = packagePath.replace("/", ".") + "entity";
            basePackage = packagePath.substring(0, packagePath.length() - 1).replace("/", ".");
            String fullEntityClassName = basePackage + ".entity." + entityClassName;


            FileResourceLoader fileResourceLoader = new FileResourceLoader(javaTemplatePath, "utf-8");

            try {
                cfg = Configuration.defaultConfiguration();
                cfg.setCharset("utf-8");//设置文件的字符集
            } catch (IOException e) {
                e.printStackTrace();
            }
            //开始逐个生成，先生成实体类
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/EntityTemplate.java");
            t.binding("packageName", packageName);
            t.binding("tableDesc", tableDesc);
            t.binding("tableName", tableName);
            t.binding("entityClassName", entityClassName);
            t.binding("fieldList", colMapList);
            String str = null;
            try {
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String saveFilePath = rootProjectPath + "/" + modulePath + "/src/main/java/" + packagePath + "entity/" + entityClassName + ".java";
            //生成到文件
            //数据库表关联的包的路径
            try {
                FileUtil.str2file(str, saveFilePath, "utf-8");
            } catch (Exception e) {

                e.printStackTrace();
            }
            log.info("开始生成参数类......");

            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/ParamTemplate.java");
            t.binding("basePackage", basePackage);
            t.binding("fullEntityClassName", fullEntityClassName);
            t.binding("entityClassName", entityClassName);
            str = "";
            try {
                str = t.render();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //log.info("生成的文件内容:"+str);
            //生成到文件
            //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";
            String paramFileName = rootProjectPath + "/" + modulePath + "/" + "src/main/java/" + basePackage.replace(".", "/") + "/module/params/" + entityClassName + "Param.java";
            try {
                FileUtil.str2file(str, paramFileName, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("生成Mapper类......");
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/MapperTemplate.java");
            t.binding("basePackage", basePackage);
            t.binding("fullEntityClassName", fullEntityClassName);
            t.binding("entityClassName", entityClassName);
            t.binding("tableName", tableName);
            str = null;
            try {
                str = t.render();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            log.info("生成的文件内容:" + str);
            //生成到文件
            //String destFilePath = "D:\\project\\openjweb-cloud\\openjweb-dev\\src\\main\\resources\\templates\\dev\\MyDemo.java";
            paramFileName = rootProjectPath + "/" + modulePath + "/" + "src/main/java/" + basePackage.replace(".", "/") + "/mapper/" + entityClassName + "Mapper.java";
            try {
                FileUtil.str2file(str, paramFileName, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ////////////////////////
            log.info("生成Mapping.xml...........");

            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/MapperTemplate.xml");

            //截取basePackage
            t.binding("basePackage", packageName.replace(".entity", ""));
            t.binding("fullClassName", packageName + "." + entityClassName);
            t.binding("packageName", packageName);
            t.binding("tableDesc", tableDesc);
            t.binding("tableName", tableName);
            t.binding("entityClassName", entityClassName);
            t.binding("fieldList", colMapList);
            str = null;
            try {
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            paramFileName = rootProjectPath + "/" + modulePath + "/" + "src/main/java/" + basePackage.replace(".", "/") + "/mapper/mapping/" + entityClassName + "Mapper.xml";

            try {
                FileUtil.str2file(str, paramFileName, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("开始生成Service类..............");
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/ServiceTemplate.java");
            //截取basePackage
            t.binding("basePackage", packageName.replace(".entity", ""));
            t.binding("fullClassName", packageName + "." + entityClassName);
            t.binding("packageName", packageName);
            t.binding("classNameLower", classNameLower);
            t.binding("tableDesc", tableDesc);
            t.binding("tableName", tableName);
            t.binding("entityClassName", entityClassName);
            t.binding("fieldList", colMapList);
            str = null;
            try {
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            paramFileName = rootProjectPath + "/" + modulePath + "/" + "src/main/java/" + basePackage.replace(".", "/") + "/service/" + entityClassName + "Service.java";
            try {
                FileUtil.str2file(str, paramFileName, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("生成控制层类...........");
            gt = new GroupTemplate(fileResourceLoader, cfg);
            t = gt.getTemplate("dev/ControllerTemplate.java");
            t.binding("basePackage", packageName.replace(".entity", ""));
            t.binding("fullClassName", packageName + "." + entityClassName);
            t.binding("packageName", packageName);
            t.binding("classNameLower", classNameLower);
            t.binding("tableDesc", tableDesc);
            t.binding("tableName", tableName);
            t.binding("entityClassName", entityClassName);
            t.binding("fieldList", colMapList);
            str = null;
            try {
                str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
                str = str.replace("/demo/api/" + classNameLower, replaceApi);//改为新标准

            } catch (Exception ex) {
                ex.printStackTrace();

            }
            paramFileName = rootProjectPath + "/" + modulePath + "/" + "src/main/java/" + basePackage.replace(".", "/") + "/api/" + entityClassName + "Api.java";
            try {
                FileUtil.str2file(str, paramFileName, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            String tmpStr = StringUtil.getFieldNameByColName(tableName.substring(tableName.indexOf("_") + 1));

            if (isCreateVue) {
                log.info("在" + vuePath + "/src/config/api.js末尾中添加接口声明。。。。。。。。。。。。。");
                String insertApi = "\r\nexport const " + classNameLower + "Api = {\r\n" +
                        //具体接口的定义规则
                        "    API_" + tableName.toUpperCase() + "_LIST: '/api/" + subSysCode + "/" + tmpStr + "/query',\r\n" +
                        "    API_" + tableName.toUpperCase() + "_LIST_DETAIL: '/api/" + subSysCode + "/" + tmpStr + "/edit',\r\n" +
                        "    API_" + tableName.toUpperCase() + "_LIST_SAVE: '/api/" + subSysCode + "/" + tmpStr + "/save',\r\n" +
                        "    API_" + tableName.toUpperCase() + "_DELETE: '/api/" + subSysCode + "/" + tmpStr + "/del'\r\n" +
                        "}\r\n\r\n";
                log.info("配置了需要生成VUE页面.......");
                log.info("生成VUE接口类文件...........");
                gt = new GroupTemplate(fileResourceLoader, cfg);
                t = gt.getTemplate("dev/VueApiTemplate.txt");
                t.binding("basePackage", packageName.replace(".entity", ""));
                t.binding("fullClassName", packageName + "." + entityClassName);
                t.binding("packageName", packageName);
                t.binding("classNameLower", classNameLower);
                t.binding("tableDesc", tableDesc);
                t.binding("tableName", tableName);
                t.binding("entityClassName", entityClassName);
                t.binding("fieldList", colMapList);
                t.binding("API_METHOD_UPPER", tableName.toUpperCase());
                //String tmpStr = StringUtil.get
                //API_METHOD_UPPER
                str = null;
                try {
                    str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                paramFileName = vuePath + "/src/api/" + classNameLower + ".js";//API接口定义文件
                log.info("vue js 文件路径:" + paramFileName);
                try {
                    //等列表页编辑页模版做完后一起启用
                    //FileUtil.str2file(str, paramFileName, "utf-8");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //同时把接口声明添加到src/config.js中
                String configFile = "";
                try {
                    configFile = FileUtil.getTextFileContent(vuePath + "/src/config/api.js", "utf-8");
                } catch (Exception ex) {
                }
                String compareStr = "export const " + classNameLower + "Api";
                if (configFile.indexOf(compareStr) == -1) {
                    log.info("不存在接口定义，重新生成。。。。。。。。。。");
                    configFile += "\r" + insertApi;
                    try {
                        //等列表页编辑页模版做完后一起启用
                        //FileUtil.str2file(configFile, vuePath + "/src/config/api.js", "utf-8");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    log.info("VUE config.js接口定义已加，不重复增加...................");
                }
                log.info("生成VUE列表页模版..................");
                //下面生成VUE列表页和编辑页，存储路径为src/views/子系统编码/类去前缀/index.vue及components/类名.vue
                //页面模版文件名vueListTemplate.vue及vueEditTemplate.vue
                gt = new GroupTemplate(fileResourceLoader, cfg);
                t = gt.getTemplate("dev/VueListPageTemplate.vue");
                t.binding("basePackage", packageName.replace(".entity", ""));
                t.binding("fullClassName", packageName + "." + entityClassName);
                t.binding("packageName", packageName);
                t.binding("classNameLower", classNameLower);
                t.binding("tableDesc", tableDesc);
                t.binding("tableName", tableName);
                t.binding("entityClassName", entityClassName);
                t.binding("fieldList", colMapList);
                //增加VUE列表页字段

                str = null;
                try {
                    str = t.render();//模版中的中文昨天没乱码，怎么今天有乱码了？


                } catch (Exception ex) {
                    ex.printStackTrace();

                }
                paramFileName = vuePath + "/src/views/" +subSysCode+"/"+ tmpStr + ".vue";//API接口定义文件
                try {
                    //这个后面再说，模版还需要调整
                    //FileUtil.str2file(str , paramFileName, "utf-8");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
            }
        }
    }
}
