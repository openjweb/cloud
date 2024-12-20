
package org.openjweb.cms.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.cms.entity.CmsCategory;
import org.openjweb.cms.module.params.CmsCategoryParam;
import org.openjweb.cms.service.CmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "网站栏目基本信息")
@Slf4j
@RestController
@RequestMapping("api/cms/category")
public class CmsCategoryApi {
    @Autowired
    private CmsCategoryService cmsCategoryService;

    /**
     * 新增记录

     */

    @ApiOperation("保存网站栏目基本信息")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "cateCode", value = "栏目编码", paramType = "query"),
           @ApiImplicitParam(name = "cateDesc", value = "栏目说明", paramType = "query"),
           @ApiImplicitParam(name = "cateDomainName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "cateDynamicUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "cateName", value = "栏目名称", paramType = "query"),
           @ApiImplicitParam(name = "catePicPath", value = "如此栏目需要附带一个图片", paramType = "query"),
           @ApiImplicitParam(name = "cateSaticUrl", value = "为提高访问效率，如果使用静态网页，则使用此字段", paramType = "query"),
           @ApiImplicitParam(name = "cateType", value = "栏目类型", paramType = "query"),
           @ApiImplicitParam(name = "comId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "", paramType = "query"),
           @ApiImplicitParam(name = "infoJspTemplate", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isAuthControl", value = "是否权限控制，改为搜索根栏目，", paramType = "query"),
           @ApiImplicitParam(name = "isHttps", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isInfoContainer", value = "标识是显示信息列表Y还是单条信息N", paramType = "query"),
           @ApiImplicitParam(name = "isInSitemap", value = "如果是，则网站地图页面显示此栏目", paramType = "query"),
           @ApiImplicitParam(name = "isLuceneSearch", value = "是否在首页的全文检索栏目下拉中显示", paramType = "query"),
           @ApiImplicitParam(name = "isNavigatorMenu", value = "是否门户页面的导航条中的一个连接", paramType = "query"),
           @ApiImplicitParam(name = "isPrepubJms", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isPubJms", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isPubRss", value = "", paramType = "query"),
           @ApiImplicitParam(name = "langType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "nginxProxyDir", value = "一般静态页根目录下的信息发布目录", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "pkId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "rootSitePath", value = "发布静态页不同网站放不同路径", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "stopDt", value = "栏目停用时间", paramType = "query"),
           @ApiImplicitParam(name = "treenodeIcon", value = "", paramType = "query"),
           @ApiImplicitParam(name = "treeCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CmsCategoryParam param){
        try {
            log.info("新增记录调用开始..........");


            this.cmsCategoryService.save(param);
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
    public ResponseResult saveOrUpdate( CmsCategoryParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CmsCategory ent = this.cmsCategoryService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setCateCode(param.getCateCode());
                ent.setCateDesc(param.getCateDesc());
                ent.setCateDomainName(param.getCateDomainName());
                ent.setCateDynamicUrl(param.getCateDynamicUrl());
                ent.setCateName(param.getCateName());
                ent.setCatePicPath(param.getCatePicPath());
                ent.setCateSaticUrl(param.getCateSaticUrl());
                ent.setCateType(param.getCateType());
                ent.setComId(param.getComId());
                ent.setCreateDt(param.getCreateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setDataFlg(param.getDataFlg());
                ent.setFlowStatus(param.getFlowStatus());
                ent.setInfoJspTemplate(param.getInfoJspTemplate());
                ent.setIsAuthControl(param.getIsAuthControl());
                ent.setIsHttps(param.getIsHttps());
                ent.setIsInfoContainer(param.getIsInfoContainer());
                ent.setIsInSitemap(param.getIsInSitemap());
                ent.setIsLuceneSearch(param.getIsLuceneSearch());
                ent.setIsNavigatorMenu(param.getIsNavigatorMenu());
                ent.setIsPrepubJms(param.getIsPrepubJms());
                ent.setIsPubJms(param.getIsPubJms());
                ent.setIsPubRss(param.getIsPubRss());
                ent.setLangType(param.getLangType());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setNginxProxyDir(param.getNginxProxyDir());
                ent.setObjName(param.getObjName());
                ent.setPkId(param.getPkId());
                ent.setRootSitePath(param.getRootSitePath());
                ent.setRowId(param.getRowId());
                ent.setSortNo(param.getSortNo());
                ent.setStopDt(param.getStopDt());
                ent.setTreenodeIcon(param.getTreenodeIcon());
                ent.setTreeCode(param.getTreeCode());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setUpdateUid(param.getUpdateUid());
                this.cmsCategoryService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.cmsCategoryService.save(param);
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
    public ResponseResult queryList(CmsCategoryParam param ){


        List<CmsCategory> list = null;
        try{
            list = this.cmsCategoryService.queryList(param);
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
        CmsCategory ent = this.cmsCategoryService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CmsCategoryParam param){
        IPage<CmsCategory> page = this.cmsCategoryService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
