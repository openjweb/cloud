package org.openjweb.cms.api;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.entity.PortalDynamicPic;
import org.openjweb.cms.module.params.CmsInfoParam;
import org.openjweb.cms.module.params.PortalDynamicPicParam;
import org.openjweb.cms.service.CmsInfoService;
import org.openjweb.cms.service.PortalDynamicPicService;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.core.entity.CommCompany;
import org.openjweb.core.module.params.CommCompanyParam;
import org.openjweb.core.service.CommCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "内容管理V3")
@Slf4j
@RestController
@RequestMapping("/api/cms/pub")
public class CmsInfoV3Api {

    @Autowired
    private CmsInfoService cmsInfoService;

    @Autowired
    private PortalDynamicPicService portalDynamicPicService;//幻灯片

    @Autowired
    private CommCompanyService commCompanyService;//幻灯片


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

    @RequestMapping("/getDynamicPic")

    //http://localhost:8001/api/cms/pub/getDynamicPic?pageNo=1&pageSize=10

    public List<PortalDynamicPic> getDynamicPicList(HttpServletRequest request, PortalDynamicPicParam param){
        //根据域名获取


        String domainName = CMSUtil.getDomainName(request);
        if(param==null)param = new PortalDynamicPicParam();
        if(param.getPage()==null)param.setPage(1);
        if(param.getPageSize()==null)param.setPageSize(10);
        //另外需要增加按公司信息进行过滤
        String comId = null;
        CommCompany comEnt = null;
        CommCompanyParam comParam = new CommCompanyParam();
        try{

            comParam.setDomainName(domainName);
            comEnt = commCompanyService.queryList(comParam).get(0);
        }
        catch (Exception ex){}
        if(comEnt==null){
            comId = "C0001";//默认为C0001
        }

        //String groupId = param.getPigGroupId();//这个需要上传，用来区分组ID
        List<PortalDynamicPic> picList = null;
        param.setComId(comId);//按公司过滤
        picList = this.portalDynamicPicService.queryList(param);
        return picList;

    }

}
