package org.openjweb.sys.testcase;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.module.params.CmsInfoParam;
import org.openjweb.cms.service.CmsInfoService;
import org.openjweb.cms.service.LuceneService;
import org.openjweb.sys.OpenjwebSysApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenjwebSysApplication.class)
public class TestLucene {
    @Resource
    private LuceneService luceneService;

    @Resource
    private CmsInfoService cmsInfoService;

    @Test
    public void createIndexdir(){
        try {
            log.info("测试创建索引 ...................");
            this.luceneService.initIndex();
            log.info("索引创建完毕.....................");
        }
        catch (Exception ex){
            log.info("创建索引出错:"+ex.toString());
        }

    }

    @Test
    public void createCmsIndex() throws Exception {
        List<CmsInfo> list = null;
        CmsInfoParam param = new CmsInfoParam();
        param.setFlowStatus("08");//查询状态为发布状态的新闻
        list = cmsInfoService.queryList(param );
        if(list!=null&&list.size()>0){
            log.info("开始添加cmsInfo 索引.........");
            luceneService.addIndex(list);
            log.info("开始添加cmsInfo索引结束........");
        }
    }
}
