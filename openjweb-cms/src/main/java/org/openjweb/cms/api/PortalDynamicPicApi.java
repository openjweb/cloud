
package org.openjweb.cms.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.cms.entity.PortalDynamicPic;
import org.openjweb.cms.module.params.PortalDynamicPicParam;
import org.openjweb.cms.service.PortalDynamicPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "首页动态变化的图片")
@Slf4j
@RestController
@RequestMapping("api/portal/dynamicPic")
public class PortalDynamicPicApi {
    @Autowired
    private PortalDynamicPicService portalDynamicPicService;

    /**
     * 新增记录

     */

    @ApiOperation("保存首页动态变化的图片")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "comId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowTransId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isMobile", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isMobileAdv", value = "", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "mobileUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "picTitle", value = "在图片中显示的文字", paramType = "query"),
           @ApiImplicitParam(name = "picUrl", value = "图片对应的URL", paramType = "query"),
           @ApiImplicitParam(name = "picUrl2", value = "", paramType = "query"),
           @ApiImplicitParam(name = "picUrlLink", value = "", paramType = "query"),
           @ApiImplicitParam(name = "pigGroupId", value = "每组图片为一套幻灯", paramType = "query"),
           @ApiImplicitParam(name = "pkId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "publishDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "storeId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "websiteCode", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( PortalDynamicPicParam param){
        try {
            log.info("新增记录调用开始..........");


            this.portalDynamicPicService.save(param);
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
    public ResponseResult saveOrUpdate( PortalDynamicPicParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            PortalDynamicPic ent = this.portalDynamicPicService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setComId(param.getComId());
                ent.setCreateDt(param.getCreateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setDataFlg(param.getDataFlg());
                ent.setFlowStatus(param.getFlowStatus());
                ent.setFlowTransId(param.getFlowTransId());
                ent.setIsMobile(param.getIsMobile());
                ent.setIsMobileAdv(param.getIsMobileAdv());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setMobileUrl(param.getMobileUrl());
                ent.setObjName(param.getObjName());
                ent.setPicTitle(param.getPicTitle());
                ent.setPicUrl(param.getPicUrl());
                ent.setPicUrl2(param.getPicUrl2());
                ent.setPicUrlLink(param.getPicUrlLink());
                ent.setPigGroupId(param.getPigGroupId());
                ent.setPkId(param.getPkId());
                ent.setPublishDt(param.getPublishDt());
                ent.setRowId(param.getRowId());
                ent.setSortNo(param.getSortNo());
                ent.setStoreId(param.getStoreId());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setWebsiteCode(param.getWebsiteCode());
                this.portalDynamicPicService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.portalDynamicPicService.save(param);
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

    @RequestMapping("query")
    public ResponseResult queryList(PortalDynamicPicParam param ){


        List<PortalDynamicPic> list = null;
        try{
            list = this.portalDynamicPicService.queryList(param);
        }
        catch(Exception ex){
            log.error("异常：：：："+ex.toString());
            return ResponseResult.errorResult(-1,ex.toString());
        }

        return ResponseResult.okResult(list);

    }



    /**
     * 查询单条记录
     * @param rowId
     * @return
     */
    @RequestMapping("edit")

    public ResponseResult edit(String rowId){
        PortalDynamicPic ent = this.portalDynamicPicService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(PortalDynamicPicParam param){
        IPage<PortalDynamicPic> page = this.portalDynamicPicService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
