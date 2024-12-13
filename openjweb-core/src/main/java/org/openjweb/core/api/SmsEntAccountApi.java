
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.SmsEntAccount;
import org.openjweb.core.module.params.SmsEntAccountParam;
import org.openjweb.core.service.SmsEntAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "直连短信供应商的短信企业帐户")
@Slf4j
@RestController
@RequestMapping("/demo/api/smsEntAccount")
public class SmsEntAccountApi {
    @Autowired
    private SmsEntAccountService smsEntAccountService;

    /**
     * 新增记录

     */

    @ApiOperation("保存直连短信供应商的短信企业帐户")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "balanceAmt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "endName", value = "用途不明", paramType = "query"),
           @ApiImplicitParam(name = "entCode", value = "企业用户由短信供应商分配的企业编码", paramType = "query"),
           @ApiImplicitParam(name = "extCode", value = "用途不明", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowTransId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isDefault", value = "缺省使用的短信帐号", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "proxyUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "recordVersion", value = "", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "signWords", value = "签名短信发送到达率高", paramType = "query"),
           @ApiImplicitParam(name = "smsSupCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "smsType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "smsUserid", value = "有的短信商友USERID参数", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "stCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "userCnName", value = "区分不同供应商下不同的用户", paramType = "query"),
           @ApiImplicitParam(name = "userName", value = "由短信运营商分配的短信用户名", paramType = "query"),
           @ApiImplicitParam(name = "userPassword", value = "发送短信使用的密码", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( SmsEntAccountParam param){
        try {
            log.info("新增记录调用开始..........");


            this.smsEntAccountService.save(param);
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
    public ResponseResult saveOrUpdate( SmsEntAccountParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            SmsEntAccount ent = this.smsEntAccountService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setBalanceAmt(param.getBalanceAmt());
                ent.setCreateDt(param.getCreateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setDataFlg(param.getDataFlg());
                ent.setEndName(param.getEndName());
                ent.setEntCode(param.getEntCode());
                ent.setExtCode(param.getExtCode());
                ent.setFlowStatus(param.getFlowStatus());
                ent.setFlowTransId(param.getFlowTransId());
                ent.setIsDefault(param.getIsDefault());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setObjName(param.getObjName());
                ent.setProxyUrl(param.getProxyUrl());
                ent.setRecordVersion(param.getRecordVersion());
                ent.setRowId(param.getRowId());
                ent.setSignWords(param.getSignWords());
                ent.setSmsSupCode(param.getSmsSupCode());
                ent.setSmsType(param.getSmsType());
                ent.setSmsUserid(param.getSmsUserid());
                ent.setSortNo(param.getSortNo());
                ent.setStCode(param.getStCode());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setUserCnName(param.getUserCnName());
                ent.setUserName(param.getUserName());
                ent.setUserPassword(param.getUserPassword());
                this.smsEntAccountService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.smsEntAccountService.save(param);
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
    public ResponseResult queryList(SmsEntAccountParam param ){


        List<SmsEntAccount> list = null;
        try{
            list = this.smsEntAccountService.queryList(param);
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
        SmsEntAccount ent = this.smsEntAccountService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(SmsEntAccountParam param){
        IPage<SmsEntAccount> page = this.smsEntAccountService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
