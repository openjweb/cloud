
package org.openjweb.cms.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.module.params.CmsInfoParam;
import org.openjweb.cms.service.CmsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "内容管理")
@Slf4j
@RestController
@RequestMapping("/demo/api/cmsInfo2") //因为有个Demo了，这里改为cmsInfo2
public class CmsInfoV2Api { //因为有个Demo了，这里改为cmsInfo2
    @Autowired
    private CmsInfoService cmsInfoService;

    /**
     * 新增记录

     */

    @ApiOperation("保存内容管理")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "actType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bannerPic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "cateTreeCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dateNum", value = "", paramType = "query"),
           @ApiImplicitParam(name = "deptName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "deptRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "", paramType = "query"),
           @ApiImplicitParam(name = "hasSmallImage", value = "", paramType = "query"),
           @ApiImplicitParam(name = "industName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "infoJspPage", value = "个别信息需要单独的页面样式", paramType = "query"),
           @ApiImplicitParam(name = "infAuthor", value = "信息作者", paramType = "query"),
           @ApiImplicitParam(name = "infClickCount", value = "点击数", paramType = "query"),
           @ApiImplicitParam(name = "infContent", value = "信息正文", paramType = "query"),
           @ApiImplicitParam(name = "infKeyword", value = "关键词", paramType = "query"),
           @ApiImplicitParam(name = "infPicCount", value = "", paramType = "query"),
           @ApiImplicitParam(name = "infSource", value = "信息来源", paramType = "query"),
           @ApiImplicitParam(name = "infTitle", value = "信息在首页显示的标题", paramType = "query"),
           @ApiImplicitParam(name = "infTitle2", value = "详细信息页面显示的标题", paramType = "query"),
           @ApiImplicitParam(name = "infType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "infUrl", value = "URL连接", paramType = "query"),
           @ApiImplicitParam(name = "isAddScan", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isAllowFeedback", value = "是否允许回复或评论", paramType = "query"),
           @ApiImplicitParam(name = "isCateFirst", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isCreateHtml", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isEndVideo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isHot", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isJp", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isPic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isRecommend", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isTop", value = "在所属栏目中置顶", paramType = "query"),
           @ApiImplicitParam(name = "isTrain", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isUrl", value = "是否URL连接(URL连接不需要正文)", paramType = "query"),
           @ApiImplicitParam(name = "isVideo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "largeImagePath", value = "", paramType = "query"),
           @ApiImplicitParam(name = "lastSyncDt", value = "最后一次同步服务器的时间", paramType = "query"),
           @ApiImplicitParam(name = "m3u8Url", value = "", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "mediaExt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "mediaId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "mediaProvider", value = "如baidu", paramType = "query"),
           @ApiImplicitParam(name = "mediaThumb", value = "缩略图URL地址", paramType = "query"),
           @ApiImplicitParam(name = "middleImagePath", value = "", paramType = "query"),
           @ApiImplicitParam(name = "mobileSortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "monthNum", value = "", paramType = "query"),
           @ApiImplicitParam(name = "njContType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "njExgType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "njInfSource", value = "", paramType = "query"),
           @ApiImplicitParam(name = "njSubContType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "njSubExgType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "njYear", value = "", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "未确定是否使用", paramType = "query"),
           @ApiImplicitParam(name = "otherProvince", value = "", paramType = "query"),
           @ApiImplicitParam(name = "pkId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "priorityValue", value = "权重，越小越靠前", paramType = "query"),
           @ApiImplicitParam(name = "provinceId", value = "有的信息是与省份关联的", paramType = "query"),
           @ApiImplicitParam(name = "publishDt", value = "信息发布日期", paramType = "query"),
           @ApiImplicitParam(name = "pubId", value = "根据推广ID生成推广页,如0001", paramType = "query"),
           @ApiImplicitParam(name = "pubUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "relateRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "slideImagePath", value = "", paramType = "query"),
           @ApiImplicitParam(name = "smallImagePath", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sourceUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "subTitle", value = "", paramType = "query"),
           @ApiImplicitParam(name = "subZtUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "teacherDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "teacherName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "teacherPhoto", value = "", paramType = "query"),
           @ApiImplicitParam(name = "trainVideoType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "unitPrice", value = "为集成商品搜索，为商品增加单价字段", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "videoCount", value = "", paramType = "query"),
           @ApiImplicitParam(name = "videoSourcePath", value = "", paramType = "query"),
           @ApiImplicitParam(name = "videoUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "wordCount", value = "", paramType = "query"),
           @ApiImplicitParam(name = "zhuantiTree", value = "", paramType = "query"),
           @ApiImplicitParam(name = "ztPicTreeCode", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CmsInfoParam param){
        try {
            log.info("新增记录调用开始..........");


            this.cmsInfoService.save(param);
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
    public ResponseResult saveOrUpdate( CmsInfoParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CmsInfo ent = this.cmsInfoService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setActType(param.getActType());
                ent.setBannerPic(param.getBannerPic());
                ent.setCateTreeCode(param.getCateTreeCode());
                ent.setComId(param.getComId());
                ent.setCreateDt(param.getCreateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setDataFlg(param.getDataFlg());
                ent.setDateNum(param.getDateNum());
                ent.setDeptName(param.getDeptName());
                ent.setDeptRowId(param.getDeptRowId());
                ent.setFlowStatus(param.getFlowStatus());
                ent.setHasSmallImage(param.getHasSmallImage());
                ent.setIndustName(param.getIndustName());
                ent.setInfoJspPage(param.getInfoJspPage());
                ent.setInfAuthor(param.getInfAuthor());
                ent.setInfClickCount(param.getInfClickCount());
                ent.setInfContent(param.getInfContent());
                ent.setInfKeyword(param.getInfKeyword());
                ent.setInfPicCount(param.getInfPicCount());
                ent.setInfSource(param.getInfSource());
                ent.setInfTitle(param.getInfTitle());
                ent.setInfTitle2(param.getInfTitle2());
                ent.setInfType(param.getInfType());
                ent.setInfUrl(param.getInfUrl());
                ent.setIsAddScan(param.getIsAddScan());
                ent.setIsAllowFeedback(param.getIsAllowFeedback());
                ent.setIsCateFirst(param.getIsCateFirst());
                ent.setIsCreateHtml(param.getIsCreateHtml());
                ent.setIsEndVideo(param.getIsEndVideo());
                ent.setIsHot(param.getIsHot());
                ent.setIsJp(param.getIsJp());
                ent.setIsPic(param.getIsPic());
                ent.setIsRecommend(param.getIsRecommend());
                ent.setIsTop(param.getIsTop());
                ent.setIsTrain(param.getIsTrain());
                ent.setIsUrl(param.getIsUrl());
                ent.setIsVideo(param.getIsVideo());
                ent.setLargeImagePath(param.getLargeImagePath());
                ent.setLastSyncDt(param.getLastSyncDt());
                ent.setM3u8Url(param.getM3u8Url());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setMediaExt(param.getMediaExt());
                ent.setMediaId(param.getMediaId());
                ent.setMediaProvider(param.getMediaProvider());
                ent.setMediaThumb(param.getMediaThumb());
                ent.setMiddleImagePath(param.getMiddleImagePath());
                ent.setMobileSortNo(param.getMobileSortNo());
                ent.setMonthNum(param.getMonthNum());
                ent.setNjContType(param.getNjContType());
                ent.setNjExgType(param.getNjExgType());
                ent.setNjInfSource(param.getNjInfSource());
                ent.setNjSubContType(param.getNjSubContType());
                ent.setNjSubExgType(param.getNjSubExgType());
                ent.setNjYear(param.getNjYear());
                ent.setObjName(param.getObjName());
                ent.setOtherProvince(param.getOtherProvince());
                ent.setPkId(param.getPkId());
                ent.setPriorityValue(param.getPriorityValue());
                ent.setProvinceId(param.getProvinceId());
                ent.setPublishDt(param.getPublishDt());
                ent.setPubId(param.getPubId());
                ent.setPubUrl(param.getPubUrl());
                ent.setRelateRowId(param.getRelateRowId());
                ent.setRowId(param.getRowId());
                ent.setSlideImagePath(param.getSlideImagePath());
                ent.setSmallImagePath(param.getSmallImagePath());
                ent.setSortNo(param.getSortNo());
                ent.setSourceUrl(param.getSourceUrl());
                ent.setSubTitle(param.getSubTitle());
                ent.setSubZtUrl(param.getSubZtUrl());
                ent.setTeacherDesc(param.getTeacherDesc());
                ent.setTeacherName(param.getTeacherName());
                ent.setTeacherPhoto(param.getTeacherPhoto());
                ent.setTrainVideoType(param.getTrainVideoType());
                ent.setUnitPrice(param.getUnitPrice());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setVideoCount(param.getVideoCount());
                ent.setVideoSourcePath(param.getVideoSourcePath());
                ent.setVideoUrl(param.getVideoUrl());
                ent.setWordCount(param.getWordCount());
                ent.setZhuantiTree(param.getZhuantiTree());
                ent.setZtPicTreeCode(param.getZtPicTreeCode());
                this.cmsInfoService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.cmsInfoService.save(param);
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
    public ResponseResult queryList(CmsInfoParam param ){


        List<CmsInfo> list = null;
        try{
            list = this.cmsInfoService.queryList(param);
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
        CmsInfo ent = this.cmsInfoService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CmsInfoParam param){
        IPage<CmsInfo> page = this.cmsInfoService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
