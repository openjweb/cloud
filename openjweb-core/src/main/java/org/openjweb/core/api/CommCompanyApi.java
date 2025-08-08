
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.CommCompany;
import org.openjweb.core.module.params.CommCompanyParam;
import org.openjweb.core.service.CommCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "公司基本信息表")
@Slf4j
@RestController
@RequestMapping("api/comm/company")
public class CommCompanyApi {
    @Autowired
    private CommCompanyService commCompanyService;

    /**
     * 新增记录

     */

    @ApiOperation("保存公司基本信息表")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "appSecret", value = "", paramType = "query"),
           @ApiImplicitParam(name = "areaName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "backLogo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bankAcctNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bankAddr", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bankComName", value = "在银行开户的公司名称", paramType = "query"),
           @ApiImplicitParam(name = "bankName", value = "开户银行详细地址", paramType = "query"),
           @ApiImplicitParam(name = "bankType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "beianNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bizDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bizDesc2", value = "", paramType = "query"),
           @ApiImplicitParam(name = "bizType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "buildDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "businessLiscenseNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "businessLiscensePic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "channelLoginId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "checkDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "checkPsn", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comAddr", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comAttr", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comBuildDt", value = "成立时间", paramType = "query"),
           @ApiImplicitParam(name = "comDesc", value = "公司备注", paramType = "query"),
           @ApiImplicitParam(name = "comFax", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comGuimo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comIdea", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comIntroduce", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comIntroduce2", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comLayoutPic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comName", value = "单位名称", paramType = "query"),
           @ApiImplicitParam(name = "comPsnNum", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comTel", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comType", value = "公司类型", paramType = "query"),
           @ApiImplicitParam(name = "comXpos", value = "", paramType = "query"),
           @ApiImplicitParam(name = "comYpos", value = "", paramType = "query"),
           @ApiImplicitParam(name = "contactMobile", value = "", paramType = "query"),
           @ApiImplicitParam(name = "contactName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "contactQq", value = "", paramType = "query"),
           @ApiImplicitParam(name = "contactTel", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "creditDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "creditLevel", value = "通淘宝信誉等级", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dealLvl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "defaultTitle", value = "", paramType = "query"),
           @ApiImplicitParam(name = "domainName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "emailAddr", value = "", paramType = "query"),
           @ApiImplicitParam(name = "feeDesc", value = "微商入驻协议", paramType = "query"),
           @ApiImplicitParam(name = "financeContact", value = "", paramType = "query"),
           @ApiImplicitParam(name = "financeTel", value = "", paramType = "query"),
           @ApiImplicitParam(name = "finOpenId", value = "财务的微信ID", paramType = "query"),
           @ApiImplicitParam(name = "healthLiscensePic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "icbcAcctName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "icbcAcctNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isBatPur", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isFirstChannel", value = "作为家政频道", paramType = "query"),
           @ApiImplicitParam(name = "isHasCommission", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isInit", value = "初始化会生成默认的公司部门和管理员", paramType = "query"),
           @ApiImplicitParam(name = "isLock", value = "是否锁定，锁定则不允许此公司的人员登录", paramType = "query"),
           @ApiImplicitParam(name = "isMember", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isNeedBuy", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isO2o", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isOrderCommission", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isPassed", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isPassEnt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isRecommend", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isSecondChannel", value = "作为商城频道", paramType = "query"),
           @ApiImplicitParam(name = "isThirdChannel", value = "作为生活娱乐频道", paramType = "query"),
           @ApiImplicitParam(name = "isVipSup", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isXiaobao", value = "", paramType = "query"),
           @ApiImplicitParam(name = "jyType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "keyWord", value = "", paramType = "query"),
           @ApiImplicitParam(name = "lawPsnId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "lawTel", value = "", paramType = "query"),
           @ApiImplicitParam(name = "lawTelDistrictCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "legalPerson", value = "", paramType = "query"),
           @ApiImplicitParam(name = "localUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "loginBanner", value = "", paramType = "query"),
           @ApiImplicitParam(name = "loginBannerVue", value = "", paramType = "query"),
           @ApiImplicitParam(name = "materialDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "miniBackPic", value = "用于小程序二维码海报合成", paramType = "query"),
           @ApiImplicitParam(name = "miniPic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "orderMobiles", value = "逗号隔开", paramType = "query"),
           @ApiImplicitParam(name = "orgLiscenseNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "orgLiscensePic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "ownerComId", value = "维护本公司数据的管理单位", paramType = "query"),
           @ApiImplicitParam(name = "payPwd", value = "", paramType = "query"),
           @ApiImplicitParam(name = "pkId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "policePic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "priorityLevel", value = "数字低的在前面", paramType = "query"),
           @ApiImplicitParam(name = "proEditPage", value = "", paramType = "query"),
           @ApiImplicitParam(name = "registCapital", value = "", paramType = "query"),
           @ApiImplicitParam(name = "registContract", value = "", paramType = "query"),
           @ApiImplicitParam(name = "registLoginId", value = "注册时填写的申请账号", paramType = "query"),
           @ApiImplicitParam(name = "registMd5Pwd", value = "", paramType = "query"),
           @ApiImplicitParam(name = "registMobileNo", value = "验证过的", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "saleSmsAccount", value = "", paramType = "query"),
           @ApiImplicitParam(name = "satifySocre", value = "", paramType = "query"),
           @ApiImplicitParam(name = "servicePromiss", value = "", paramType = "query"),
           @ApiImplicitParam(name = "serviceProCode", value = "O2O中不需要特指的商品", paramType = "query"),
           @ApiImplicitParam(name = "settleDay", value = "", paramType = "query"),
           @ApiImplicitParam(name = "shopLocation", value = "例如北京丰台体验馆", paramType = "query"),
           @ApiImplicitParam(name = "shopOpenTime", value = "例如9：00-21：00", paramType = "query"),
           @ApiImplicitParam(name = "shopTemplate", value = "对于开通商城的选择商城模板", paramType = "query"),
           @ApiImplicitParam(name = "smsAccount", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "storeType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "supApCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "supLvl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sysName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "taxCertPic", value = "", paramType = "query"),
           @ApiImplicitParam(name = "taxNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "urlAddr", value = "", paramType = "query"),
           @ApiImplicitParam(name = "watermarkImgPath", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CommCompanyParam param){
        try {
            log.info("新增记录调用开始..........");


            this.commCompanyService.save(param);
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
    public ResponseResult saveOrUpdate( CommCompanyParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CommCompany ent = this.commCompanyService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setAppSecret(param.getAppSecret());
                ent.setAreaName(param.getAreaName());
                ent.setBackLogo(param.getBackLogo());
                ent.setBankAcctNo(param.getBankAcctNo());
                ent.setBankAddr(param.getBankAddr());
                ent.setBankComName(param.getBankComName());
                ent.setBankName(param.getBankName());
                ent.setBankType(param.getBankType());
                ent.setBeianNo(param.getBeianNo());
                ent.setBizDesc(param.getBizDesc());
                ent.setBizDesc2(param.getBizDesc2());
                ent.setBizType(param.getBizType());

                ent.setBusinessLiscenseNo(param.getBusinessLiscenseNo());
                ent.setBusinessLiscensePic(param.getBusinessLiscensePic());
                ent.setChannelLoginId(param.getChannelLoginId());
                ent.setCheckDt(param.getCheckDt());
                ent.setCheckPsn(param.getCheckPsn());
                ent.setComAddr(param.getComAddr());
                ent.setComAttr(param.getComAttr());
                ent.setComBuildDt(param.getComBuildDt());
                ent.setComDesc(param.getComDesc());
                ent.setComFax(param.getComFax());
                ent.setComGuimo(param.getComGuimo());
                ent.setComIdea(param.getComIdea());
                ent.setComIntroduce(param.getComIntroduce());
                ent.setComIntroduce2(param.getComIntroduce2());
                ent.setComLayoutPic(param.getComLayoutPic());
                ent.setComName(param.getComName());
                ent.setComPsnNum(param.getComPsnNum());
                ent.setComTel(param.getComTel());
                ent.setComType(param.getComType());
                ent.setComXpos(param.getComXpos());
                ent.setComYpos(param.getComYpos());
                ent.setContactMobile(param.getContactMobile());
                ent.setContactName(param.getContactName());
                ent.setContactQq(param.getContactQq());
                ent.setContactTel(param.getContactTel());
                ent.setCreateDt(param.getCreateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setCreditDesc(param.getCreditDesc());
                ent.setCreditLevel(param.getCreditLevel());
                ent.setDataFlg(param.getDataFlg());
                ent.setDealLvl(param.getDealLvl());
                ent.setDefaultTitle(param.getDefaultTitle());
                ent.setDomainName(param.getDomainName());
                ent.setEmailAddr(param.getEmailAddr());
                ent.setFeeDesc(param.getFeeDesc());
                ent.setFinanceContact(param.getFinanceContact());
                ent.setFinanceTel(param.getFinanceTel());
                ent.setFinOpenId(param.getFinOpenId());
                ent.setHealthLiscensePic(param.getHealthLiscensePic());
                ent.setIcbcAcctName(param.getIcbcAcctName());
                ent.setIcbcAcctNo(param.getIcbcAcctNo());
                ent.setIsBatPur(param.getIsBatPur());
                ent.setIsFirstChannel(param.getIsFirstChannel());
                ent.setIsHasCommission(param.getIsHasCommission());
                ent.setIsInit(param.getIsInit());
                ent.setIsLock(param.getIsLock());
                ent.setIsMember(param.getIsMember());
                ent.setIsNeedBuy(param.getIsNeedBuy());
                ent.setIsO2o(param.getIsO2o());
                ent.setIsOrderCommission(param.getIsOrderCommission());
                ent.setIsPassed(param.getIsPassed());
                ent.setIsPassEnt(param.getIsPassEnt());
                ent.setIsRecommend(param.getIsRecommend());
                ent.setIsSecondChannel(param.getIsSecondChannel());
                ent.setIsThirdChannel(param.getIsThirdChannel());
                ent.setIsVipSup(param.getIsVipSup());
                ent.setIsXiaobao(param.getIsXiaobao());
                ent.setJyType(param.getJyType());
                ent.setKeyWord(param.getKeyWord());
                ent.setLawPsnId(param.getLawPsnId());
                ent.setLawTel(param.getLawTel());
                ent.setLawTelDistrictCode(param.getLawTelDistrictCode());
                ent.setLegalPerson(param.getLegalPerson());
                ent.setLocalUrl(param.getLocalUrl());
                ent.setLoginBanner(param.getLoginBanner());
                ent.setLoginBannerVue(param.getLoginBannerVue());
                ent.setMaterialDesc(param.getMaterialDesc());
                ent.setMiniBackPic(param.getMiniBackPic());
                ent.setMiniPic(param.getMiniPic());
                ent.setOrderMobiles(param.getOrderMobiles());
                ent.setOrgLiscenseNo(param.getOrgLiscenseNo());
                ent.setOrgLiscensePic(param.getOrgLiscensePic());
                ent.setOwnerComId(param.getOwnerComId());
                ent.setPayPwd(param.getPayPwd());
                ent.setPkId(param.getPkId());
                ent.setPolicePic(param.getPolicePic());
                ent.setPriorityLevel(param.getPriorityLevel());
                ent.setProEditPage(param.getProEditPage());
                ent.setRegistCapital(param.getRegistCapital());
                ent.setRegistContract(param.getRegistContract());
                ent.setRegistLoginId(param.getRegistLoginId());
                ent.setRegistMd5Pwd(param.getRegistMd5Pwd());
                ent.setRegistMobileNo(param.getRegistMobileNo());
                ent.setRowId(param.getRowId());
                ent.setSaleSmsAccount(param.getSaleSmsAccount());
                ent.setServicePromiss(param.getServicePromiss());
                ent.setServiceProCode(param.getServiceProCode());
                ent.setSettleDay(param.getSettleDay());
                ent.setShopLocation(param.getShopLocation());
                ent.setShopOpenTime(param.getShopOpenTime());
                ent.setShopTemplate(param.getShopTemplate());
                ent.setSmsAccount(param.getSmsAccount());
                ent.setSortNo(param.getSortNo());
                ent.setStoreType(param.getStoreType());
                ent.setSupApCode(param.getSupApCode());
                ent.setSupLvl(param.getSupLvl());
                ent.setSysName(param.getSysName());
                ent.setTaxCertPic(param.getTaxCertPic());
                ent.setTaxNo(param.getTaxNo());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setUrlAddr(param.getUrlAddr());
                ent.setWatermarkImgPath(param.getWatermarkImgPath());
                this.commCompanyService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.commCompanyService.save(param);
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
    public ResponseResult queryList(CommCompanyParam param ){


        List<CommCompany> list = null;
        try{
            list = this.commCompanyService.queryList(param);
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
        CommCompany ent = this.commCompanyService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CommCompanyParam param){
        IPage<CommCompany> page = this.commCompanyService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
