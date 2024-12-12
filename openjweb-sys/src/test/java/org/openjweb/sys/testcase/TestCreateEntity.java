package org.openjweb.sys.testcase;


import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.StringUtil;
import org.openjweb.dev.util.DevUtil;
import org.openjweb.sys.OpenjwebSysApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import java.sql.SQLException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenjwebSysApplication.class)

public class TestCreateEntity {
    @Test
    public void createEntity() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        //String tableName = "comm_api_key";
        String tableName = "comm_system_parms";
        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        //DevUtil.createEntityFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","d:\\"+clsName+".java");
        DevUtil.createEntityFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","D:/project/openjweb-cloud/openjweb-core/src/main/java/org/openjweb/core/entity/"+clsName+".java");

    }

    @Test
    public void createParamFile() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        //String tableName = "comm_api_key";
        String tableName = "comm_system_parms";

        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        DevUtil.createParamFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core","D:/project/openjweb-cloud/openjweb-core");

    }

    @Test
    public void createMapperFile() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        String tableName = "comm_system_parms";
        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        DevUtil.createMapperFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core","D:/project/openjweb-cloud/openjweb-core");

    }

    @Test
    public void createXmlFile() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        //String tableName = "comm_api_key";
        String tableName = "comm_system_parms";
        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        //DevUtil.createEntityFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","d:\\"+clsName+".java");
        DevUtil.createXmlFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","D:/project/openjweb-cloud/openjweb-core/src/main/java/org/openjweb/core/mapper/mapping/"+clsName+"Mapper.xml");

    }

    @Test
    public void createServiceFile() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        //String tableName = "comm_api_key";
        String tableName = "comm_system_parms";
        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        //DevUtil.createEntityFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","d:\\"+clsName+".java");
        DevUtil.createServiceFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","D:/project/openjweb-cloud/openjweb-core/src/main/java/org/openjweb/core/service/"+clsName+"Service.java");

    }

    @Test
    public void createApiFile() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        //String tableName = "comm_api_key";
        String tableName = "comm_system_parms";
        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        //DevUtil.createEntityFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","d:\\"+clsName+".java");
        DevUtil.createApiFile("com.mysql.jdbc.Driver",url,schemaName,username,password,tableName,"org.openjweb.core.entity","D:/project/openjweb-cloud/openjweb-core/src/main/java/org/openjweb/core/api/"+clsName+"Api.java");

    }
}
