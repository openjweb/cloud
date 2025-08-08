
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.CommSystemParms;
import org.openjweb.core.module.params.CommSystemParmsParam;
import org.openjweb.core.service.CommSystemParmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "")
@Slf4j
@RestController
@RequestMapping("api/comm/systemParms")
public class CommSystemParmsApi {
    @Autowired
    private CommSystemParmsService commSystemParmsService;

    /**
     * 新增记录

     */

    @ApiOperation("保存")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "parmName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "parmValue", value = "", paramType = "query"),
           @ApiImplicitParam(name = "parmDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "parmOldValues", value = "存储此参数项的其他参数值，以便实施过程中根据不同项目配置", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "pkId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "objName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "masterRowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowTransId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "flowStatus", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CommSystemParmsParam param){
        try {
            log.info("新增记录调用开始..........");


            this.commSystemParmsService.save(param);
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
    public ResponseResult saveOrUpdate( CommSystemParmsParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CommSystemParms ent = this.commSystemParmsService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setParmName(param.getParmName());
                ent.setParmValue(param.getParmValue());
                ent.setParmDesc(param.getParmDesc());
                ent.setParmOldValues(param.getParmOldValues());
                ent.setRowId(param.getRowId());
                ent.setSortNo(param.getSortNo());
                ent.setCreateDt(param.getCreateDt());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setPkId(param.getPkId());
                ent.setObjName(param.getObjName());
                ent.setMasterRowId(param.getMasterRowId());
                ent.setFlowTransId(param.getFlowTransId());
                ent.setDataFlg(param.getDataFlg());
                ent.setFlowStatus(param.getFlowStatus());
                this.commSystemParmsService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.commSystemParmsService.save(param);
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
    public ResponseResult queryList(CommSystemParmsParam param ){


        List<CommSystemParms> list = null;
        try{
            list = this.commSystemParmsService.queryList(param);
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
        CommSystemParms ent = this.commSystemParmsService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CommSystemParmsParam param){
        IPage<CommSystemParms> page = this.commSystemParmsService.findPage(param);
        return ResponseResult.okResult(page, 0, "查询完毕！");

    }

    @RequestMapping("del")
    public ResponseResult delWebsite(String rowId){
        try {
            this.commSystemParmsService.del(rowId);
            return ResponseResult.okResult(0,"删除成功!");
        }
        catch(Exception ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"删除失败!");
        }
    }

}
