package org.openjweb.sys.testcase;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.dev.entity.TableColumnInfo;
import org.openjweb.sys.OpenjwebSysApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.openjweb.dev.util.DbUtil;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenjwebSysApplication.class)
//@TestPropertySource(locations = "classpath:application-dev.yml")
public class TestCreateTable {
    //@Value("${spring.datasource.ds1.url:}") String dbUrl;

    @Test
    public void getTableComment() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String schemaName = "erp";
        String tableComment = DbUtil.getTableComment("com.mysql.jdbc.Driver",url,schemaName,username,password,"comm_system_parms");
        log.info("查询到的表注释为:");
        log.info(tableComment);
        log.info("读取表字段信息：：：：：");
        List<TableColumnInfo> colList =
                DbUtil.getTableColumnInfo("com.mysql.jdbc.Driver",url,schemaName,username,password,"comm_system_parms");
        if(colList!=null&& colList.size()>0){
            for(int i=0;i<colList.size();i++){
                TableColumnInfo ent = colList.get(i);
                log.info(ent.toString());
            }
        }

    }
}
