package org.openjweb.cms.api;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.module.params.CmsInfoParam;
import org.openjweb.cms.service.CmsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "内容管理V3")
@Slf4j
@RestController
@RequestMapping("/api/cms/pub")
public class CmsInfoV3Api {

    @Autowired
    private CmsInfoService cmsInfoService;

    @RequestMapping("/getCateInfoList")
    public List<CmsInfo> getCateCmsInfo(String treeCode, int pageNo, int pageSize){
        log.info("getCateInfoList传入的参数：");
        log.info(treeCode);
        log.info(String.valueOf(pageNo));
        log.info(String.valueOf(pageSize));

        CmsInfoParam param = new CmsInfoParam();
        param.setPage(pageNo);
        param.setPageSize(pageSize);
        param.setCateTreeCode(treeCode);
        List<CmsInfo> list = this.cmsInfoService.queryList(param);
        if(list!=null&&list.size()>0){
            //${myService.sayHello('World')}
            log.info("查询到栏目的信息......");
        }
        else{
            log.info("没查到栏目下的信息.....");
        }
        return list;
        //List<CmsInfo> cmsInfo



    }
}
