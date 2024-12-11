package org.openjweb.sys.testcase;


import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.openjweb.core.service.CommApiKeyService;
import org.openjweb.sys.OpenjwebSysApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenjwebSysApplication.class)
public class TestCommApiKey {
    @Autowired
    private CommApiKeyService commApiKeyService;
    @Test
    public void testQueryList() throws SQLException {
        CommApiKeyParam param = new CommApiKeyParam();

        List<CommApiKey> list = this.commApiKeyService.queryList(param);
        if(list!=null){
            log.info("没查到数据....");
        }
        else{
            log.info("查到数据...."+list.size());
        }

    }
}
