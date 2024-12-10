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
        String tableName = "comm_api_key";
        String clsName = StringUtil.getEntityNameByTableName(tableName);
        //String
        DevUtil.createEntityFile("com.mysql.jdbc.Driver",url,schemaName,username,password,"comm_system_parms","org.openjweb.core.entity","d:\\"+clsName+".java");

    }
}
