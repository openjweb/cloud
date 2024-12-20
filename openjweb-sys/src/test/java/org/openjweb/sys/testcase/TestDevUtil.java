package org.openjweb.sys.testcase;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjweb.dev.util.DevUtilV2;
import org.openjweb.sys.OpenjwebSysApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenjwebSysApplication.class)
public class TestDevUtil {
    @Resource
    private DevUtilV2 util;

    @Test
    public void createJavaSource(){
        //String tableName = "b2c_pro_info";//生成商品基本信息表
        log.info("TestDevUtil开始生成源码.........");
        //String tableName = "cms_info";//生成cms_info相关增删改查代码
        String tableName = "cms_category";//生成cms_info相关增删改查代码

        util.createJavaSource(tableName);
        log.info("TestDevUtil开始生成源码.结束........");
    }
}
