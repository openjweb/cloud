package org.openjweb.cms.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.entity.PortalWebsite;
import org.openjweb.cms.module.params.CmsInfoParam;
import org.openjweb.cms.module.params.PortalWebsiteParam;
import org.openjweb.cms.service.CmsInfoService;
import org.openjweb.cms.service.PortalWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "内容管理-前端查询")
@Slf4j
@Controller
@RequestMapping("/front") //

public class CmsInfoController {
    //测试地址： http://localhost:8001/front/index
    @Autowired
    private CmsInfoService cmsInfoService;

    @Autowired
    private PortalWebsiteService portalWebsiteService;

    @RequestMapping(value="/index")

    public String index( Model model) {

        return "cms/site/wenhua/index.html";//返回页面名
    }

    //http://localhost:8001/front/index
    @GetMapping("{pageName}")
    public String toHome(@PathVariable String pageName, Model model,HttpServletRequest request) {

        log.info("开始查询站点信息.................");

        PortalWebsite siteEnt = this.portalWebsiteService.getSiteInfo(request,new PortalWebsiteParam());
        model.addAttribute("siteName", "文化网");
        if(siteEnt==null){
            log.info("没有查到站点信息............");
        }
        else{
            log.info("查到站点信息.........");
        }
        //http://localhost:8001/front/china 在china.html里测试变量
        //京公网安备、京ICP备、版权信息、地址、主办单位、承办单位
        //siteEnt.getFullName();//网站全名-作为主办单位
        //siteEnt.getEmailAddress();//联系邮箱
        //siteEnt.getCopyrightMsg();//版权信息
        //siteEnt.getContactUs();//联系我们
        //siteEnt.getWebsiteName();//网站名称
        //后续需要新的字段就在portal_website加新的
        model.addAttribute("siteInfo",siteEnt);//测试整合实体赋值给前端

        return "cms/site/wenhua/"+pageName+".html";//返回页面名
    }

    //http://localhost:8001/front/mobile2/newmobileList
    @GetMapping("{path}/{pageName}")
    public String toSub(@PathVariable String path,@PathVariable String pageName, Model model,HttpServletRequest request) {
        Map<String,Object> map = new HashMap();
        log.info("调用的toSub.........");
        //测试前端HTML中增加变量
        model.addAttribute("siteName", "文化网");//给前端页面设置变量
        return "cms/site/wenhua/"+path+"/"+pageName+".html";//返回页面名
    }

    public List<CmsInfo> getCateCmsInfo(String cateTreeCode,int pageNo,int pageSize){
        CmsInfoParam param = new CmsInfoParam();
        param.setPage(pageNo);
        param.setPageSize(pageSize);
        param.setCateTreeCode(cateTreeCode);
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
