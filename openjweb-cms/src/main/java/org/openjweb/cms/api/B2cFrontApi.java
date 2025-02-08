package org.openjweb.cms.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.entity.PortalDynamicPic;
import org.openjweb.cms.module.params.PortalDynamicPicParam;
import org.openjweb.cms.service.PortalDynamicPicService;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.core.entity.CommCompany;
import org.openjweb.core.module.params.CommCompanyParam;
import org.openjweb.core.service.CommCompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "商城前端公共接口")
@Slf4j
@RestController
@RequestMapping("/api/b2c/pub")
public class B2cFrontApi {
    //http://localhost:8001/api/b2c/pub/getHotSearch

    @Resource
    private CommCompanyService commCompanyService;

    @Resource
    private PortalDynamicPicService portalDynamicPicService;

    @RequestMapping("/getHotSearch")
    public ResponseResult getHotSearch(HttpServletRequest request){
        String[] keys = {"数码相机","休闲零食","月饼","女装"};
        return ResponseResult.okResult(keys);
    }
    @RequestMapping("/getHomeData")
    public ResponseResult getHomeData(HttpServletRequest request){
        //http://localhost:8001/api/b2c/pub/getHomeData
        String[] navList = {"华为手机","苹果手机","固态硬盘","服装鞋帽"};//保留4个否则显示不了那么多
        Map map = new HashMap();
        map.put("hot",navList);//热搜
        //下面获轮播图
        List<PortalDynamicPic> picList = null;//导航图
        String domainName = CMSUtil.getDomainName(request);
        //按公司及应用获取导航图
        CommCompanyParam comParam = new CommCompanyParam();
        comParam.setDomainName(domainName);
        CommCompany comEnt =  null;
        String comId = "";//公司编码

        try{
            comEnt = this.commCompanyService.queryList(comParam).get(0);
            comId = comEnt.getPkId();
            if(comId==null)comId = "C0001";//

        }
        catch(Exception ex){}
        IPage<PortalDynamicPic> picIPage = null;
        try{
            PortalDynamicPicParam param = new PortalDynamicPicParam();
            param.setComId(comId);
            param.setPigGroupId(comId+"-APP-B2C");
            param.setPageSize(5);
            param.setPage(1);
            picIPage  = this.portalDynamicPicService.findPage(param);
        }
        catch (Exception ex){
            log.error(ex.toString());
        }
        if(picIPage==null){
            log.info("没查到幻灯记录..........");
        }
        else{
            log.info("查到幻灯记录>>>>");
            log.info(""+picIPage.getRecords().size());
        }

        map.put("dynamicPic",picIPage.getRecords());//分页


       return ResponseResult.okResult(map);
    }
}
