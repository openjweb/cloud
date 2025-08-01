
package org.openjweb.cms.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.cms.entity.PortalWebsite;
import org.openjweb.cms.module.params.PortalWebsiteParam;
import org.openjweb.cms.service.PortalWebsiteService;
import org.openjweb.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试：
 */
@Api(tags = "")
@Slf4j
@RestController
@RequestMapping("api/portal/website")
public class PortalWebsiteApi {
    @Autowired
    private PortalWebsiteService portalWebsiteService;

    /**
     * 新增记录

     */

    @ApiOperation("保存")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "aboutusMsg", value = "本网站的关于我们的信息", paramType = "query"),
           @ApiImplicitParam(name = "addressMsg", value = "地址信息", paramType = "query"),
           @ApiImplicitParam(name = "adminLoginId", value = "本网站的管理员帐号", paramType = "query"),
           @ApiImplicitParam(name = "comId", value = "网站所属单位,如C0001", paramType = "query"),
           @ApiImplicitParam(name = "contactUs", value = "可能同时包含google地图", paramType = "query"),
           @ApiImplicitParam(name = "copyrightMsg", value = "版权信息", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "defaultPage", value = "如index.jsp", paramType = "query"),
           @ApiImplicitParam(name = "domainName", value = "如www.xxx.com", paramType = "query"),
           @ApiImplicitParam(name = "emailAddress", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowTransId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "forbidWords", value = "发布信息时，不允许带以下敏感词", paramType = "query"),
           @ApiImplicitParam(name = "fullName", value = "有的网站不隶属单位", paramType = "query"),
           @ApiImplicitParam(name = "icpNo", value = "如京ICP备05083456号", paramType = "query"),
           @ApiImplicitParam(name = "infoSource", value = "", paramType = "query"),
           @ApiImplicitParam(name = "infListPage", value = "如/portal/wentong/xxx.jsp", paramType = "query"),
           @ApiImplicitParam(name = "isMainSite", value = "如果根据IP访问，则查找一个域名作为主站", paramType = "query"),
           @ApiImplicitParam(name = "isToBackend", value = "", paramType = "query"),
           @ApiImplicitParam(name = "lawDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "loginUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "pkId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "postCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "relatePath", value = "相对当前web应用的子目录", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "searchListPage", value = "全文检索搜索列表页模板", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "viewInfoPage", value = "展示详细信息的模板", paramType = "query"),
           @ApiImplicitParam(name = "websiteCode", value = "网站代号，如文通网wentongPortal，与栏目中所属网站对应", paramType = "query"),
           @ApiImplicitParam(name = "websiteName", value = "网站的简称", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( PortalWebsiteParam param){
        try {
            log.info("新增记录调用开始..........");


            this.portalWebsiteService.save(param);
            log.info("新增记录调用结束..........");
            return ResponseResult.okResult(0,"新增成功!");
        }
        catch(Exception  ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"新增失败!");
        }

    }

    /**
     * 新增或修改记录
     * @param param
     * @return
     */

    @RequestMapping("saveOrUpdate")
    public ResponseResult saveOrUpdate( PortalWebsiteParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            PortalWebsite ent = this.portalWebsiteService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setAboutusMsg(param.getAboutusMsg());
                ent.setAddressMsg(param.getAddressMsg());
                ent.setAdminLoginId(param.getAdminLoginId());
                ent.setComId(param.getComId());
                ent.setContactUs(param.getContactUs());
                ent.setCopyrightMsg(param.getCopyrightMsg());
                ent.setCreateDt(param.getCreateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setDataFlg(param.getDataFlg());
                ent.setDefaultPage(param.getDefaultPage());
                ent.setDomainName(param.getDomainName());
                ent.setEmailAddress(param.getEmailAddress());
                ent.setFlowStatus(param.getFlowStatus());
                ent.setFlowTransId(param.getFlowTransId());
                ent.setForbidWords(param.getForbidWords());
                ent.setFullName(param.getFullName());
                ent.setIcpNo(param.getIcpNo());
                ent.setInfoSource(param.getInfoSource());
                ent.setInfListPage(param.getInfListPage());
                ent.setIsMainSite(param.getIsMainSite());
                ent.setIsToBackend(param.getIsToBackend());
                ent.setLawDesc(param.getLawDesc());
                ent.setLoginUrl(param.getLoginUrl());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setObjName(param.getObjName());
                ent.setPkId(param.getPkId());
                ent.setPostCode(param.getPostCode());
                ent.setRelatePath(param.getRelatePath());
                ent.setRowId(param.getRowId());
                ent.setSearchListPage(param.getSearchListPage());
                ent.setSortNo(param.getSortNo());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setViewInfoPage(param.getViewInfoPage());
                ent.setWebsiteCode(param.getWebsiteCode());
                ent.setWebsiteName(param.getWebsiteName());
                this.portalWebsiteService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");
                if(StringUtil.isEmpty(param.getRowId())){
                    param.setRowId(StringUtil.getUUID());
                    param.setPkId(StringUtil.getUUID());
                }

                this.portalWebsiteService.save(param);
            }
            log.info("保存完毕..........");
        }
        catch(Exception  ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"新增失败!");
        }
        return ResponseResult.okResult(0,"success");



    }

    /**
     * 查询列表不分页
     * @param param
     * @return
     */

    @RequestMapping("query1")
    public ResponseResult queryList(PortalWebsiteParam param ){

        log.info("调用PortalWebsite开始.............");


         List<PortalWebsite> list = null;
        try{
            list = this.portalWebsiteService.queryList(param);
            log.info(String.valueOf(list.size()));
        }
        catch(Exception ex){
            log.error("异常：：：："+ex.toString());
            return ResponseResult.errorResult(-1,ex.toString());
        }
        log.info("调用PortalWebsite结束.............");





        return ResponseResult.okResult(list);


    }



    /**
     * 查询单条记录
     * @param rowId
     * @return
     */
    @RequestMapping("edit")

    public ResponseResult edit(String rowId){
        log.info("调用PortalWebsite编辑...............");
        PortalWebsite ent = this.portalWebsiteService.queryByRowId(rowId);
        log.info("返回PortalWebsite数据...............");
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    //@RequestMapping("findPage")
    @RequestMapping("query")
    public ResponseResult findPage(PortalWebsiteParam param){
        log.info("开始调用findPage........................");
        //long count = this.portalWebsiteService.selectCount(param);
        //log.info("count:::");
        //log.info(String.valueOf(count));
        //param.getPageSize();
        //param.getPage();
        IPage<PortalWebsite> page = this.portalWebsiteService. findPage(param);

        //因为要适配SSH版，所以page的内容要拿出来单独封装
        /*
        List<PortalWebsite> list = null;
        try{
            list = page.getRecords();
        }
        catch(Exception ex){}
        log.info("记录数:"+String.valueOf(count));
        log.info("list size::::");
        if(list==null){
            log.info("0");
        }
        else{
            log.info(""+list.size());
            for(PortalWebsite ent:list){
                log.info(ent.getWebsiteCode());
                log.info(ent.getWebsiteName());

            }
        }
        Map map = new HashMap();
        map.put("code",0);
        map.put("count",count);

        map.put("msg","查询完毕!");
        map.put("data",list);

         */
        log.info("开始调用findPage....返回....................");
        return ResponseResult.okResult(page, 0, "查询完毕！");
        //return map;
    }

    //少了一个删除
    @RequestMapping("del")
    public ResponseResult delWebsite(String rowId){
        log.info("删除的ROWIDS:");
        log.info(rowId);
        try {
            this.portalWebsiteService.del(rowId);
            return ResponseResult.okResult(0,"删除成功!");

        }
        catch(Exception ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"删除失败!");

        }


    }

}
