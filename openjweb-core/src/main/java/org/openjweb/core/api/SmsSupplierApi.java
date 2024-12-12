
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.SmsSupplier;
import org.openjweb.core.module.params.SmsSupplierParam;
import org.openjweb.core.service.SmsSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "短信供应商表")
@Slf4j
@RestController
@RequestMapping("/demo/api/smsSupplier")
public class SmsSupplierApi {
    @Autowired
    private SmsSupplierService smsSupplierService;

    /**
     * 新增记录

     */

    @ApiOperation("保存短信供应商表")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "rowId", value = "唯一ID", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "记录顺序", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "创建日期", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "修改日期", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "创建人", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "修改人", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "状态位", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "备注", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "父表ID", paramType = "query"),
           @ApiImplicitParam(name = "flowTransId", value = "事务ID", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "流程状态", paramType = "query"),
           @ApiImplicitParam(name = "recordVersion", value = "乐观锁", paramType = "query"),
           @ApiImplicitParam(name = "isDefault", value = "是否默认", paramType = "query"),
           @ApiImplicitParam(name = "supName", value = "短信商名称", paramType = "query"),
           @ApiImplicitParam(name = "recvMsgUrl", value = "接收消息URL", paramType = "query"),
           @ApiImplicitParam(name = "queryBalanceUrl", value = "查询余额URL", paramType = "query"),
           @ApiImplicitParam(name = "chgPassUrl", value = "修改密码URL", paramType = "query"),
           @ApiImplicitParam(name = "sendMessUrl", value = "发送短信URL", paramType = "query"),
           @ApiImplicitParam(name = "abbrName", value = "短信商简称", paramType = "query"),
           @ApiImplicitParam(name = "interfaceAddr", value = "接口地址", paramType = "query"),
           @ApiImplicitParam(name = "supCode", value = "短信商编码", paramType = "query"),
           @ApiImplicitParam(name = "provinceId", value = "省份", paramType = "query"),
           @ApiImplicitParam(name = "homePage", value = "主页", paramType = "query"),
           @ApiImplicitParam(name = "registChildUrl", value = "注册子账号URL", paramType = "query"),
           @ApiImplicitParam(name = "encodingType", value = "接口字符集", paramType = "query"),
           @ApiImplicitParam(name = "filterWordsUrl", value = "敏感词URL", paramType = "query"),
           @ApiImplicitParam(name = "recvSmsUrl", value = "收短信URL", paramType = "query"),
           @ApiImplicitParam(name = "errorUrl", value = "错误信息URL", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( SmsSupplierParam param){
        try {
            log.info("新增记录调用开始..........");


            this.smsSupplierService.save(param);
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
    public ResponseResult saveOrUpdate( SmsSupplierParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            SmsSupplier ent = this.smsSupplierService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setRowId(param.getRowId());
                ent.setSortNo(param.getSortNo());
                ent.setCreateDt(param.getCreateDt());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setDataFlg(param.getDataFlg());
                ent.setObjName(param.getObjName());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setFlowTransId(param.getFlowTransId());
                ent.setFlowStatus(param.getFlowStatus());
                ent.setRecordVersion(param.getRecordVersion());
                ent.setIsDefault(param.getIsDefault());
                ent.setSupName(param.getSupName());
                ent.setRecvMsgUrl(param.getRecvMsgUrl());
                ent.setQueryBalanceUrl(param.getQueryBalanceUrl());
                ent.setChgPassUrl(param.getChgPassUrl());
                ent.setSendMessUrl(param.getSendMessUrl());
                ent.setAbbrName(param.getAbbrName());
                ent.setInterfaceAddr(param.getInterfaceAddr());
                ent.setSupCode(param.getSupCode());
                ent.setProvinceId(param.getProvinceId());
                ent.setHomePage(param.getHomePage());
                ent.setRegistChildUrl(param.getRegistChildUrl());
                ent.setEncodingType(param.getEncodingType());
                ent.setFilterWordsUrl(param.getFilterWordsUrl());
                ent.setRecvSmsUrl(param.getRecvSmsUrl());
                ent.setErrorUrl(param.getErrorUrl());
                this.smsSupplierService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.smsSupplierService.save(param);
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
    public ResponseResult queryList(SmsSupplierParam param ){


        List<SmsSupplier> list = null;
        try{
            list = this.smsSupplierService.queryList(param);
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
        SmsSupplier ent = this.smsSupplierService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(SmsSupplierParam param){
        IPage<SmsSupplier> page = this.smsSupplierService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
