
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.CommTableDef;
import org.openjweb.core.module.params.CommTableDefParam;
import org.openjweb.core.service.CommTableDefService;
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
@RequestMapping("/demo/api/commTableDef")
public class CommTableDefApi {
    @Autowired
    private CommTableDefService commTableDefService;

    /**
     * 新增记录

     */

    @ApiOperation("保存")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "serialNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "tableName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "tableNameCn", value = "", paramType = "query"),
           @ApiImplicitParam(name = "clsName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sysCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "tableDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "tableSerial", value = "", paramType = "query"),
           @ApiImplicitParam(name = "tableDocNum", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isCreated", value = "", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isMasterTable", value = "", paramType = "query"),
           @ApiImplicitParam(name = "ifCreateService", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isExportExcel", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isTree", value = "", paramType = "query"),
           @ApiImplicitParam(name = "recordCount", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isFlowMonitor", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isSearch", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isAuthControl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isShowDictName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "colsPerRow", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isUseValidcode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isCreateSumrow", value = "", paramType = "query"),
           @ApiImplicitParam(name = "formTags", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dbServiceName", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CommTableDefParam param){
        try {
            log.info("新增记录调用开始..........");


            this.commTableDefService.save(param);
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
    public ResponseResult saveOrUpdate( CommTableDefParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CommTableDef ent = this.commTableDefService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setSerialNo(param.getSerialNo());
                ent.setTableName(param.getTableName());
                ent.setTableNameCn(param.getTableNameCn());
                ent.setClsName(param.getClsName());
                ent.setSysCode(param.getSysCode());
                ent.setTableDesc(param.getTableDesc());
                ent.setTableSerial(param.getTableSerial());
                ent.setTableDocNum(param.getTableDocNum());
                ent.setIsCreated(param.getIsCreated());
                ent.setRowId(param.getRowId());
                ent.setCreateDt(param.getCreateDt());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setSortNo(param.getSortNo());
                ent.setDataFlg(param.getDataFlg());
                ent.setIsMasterTable(param.getIsMasterTable());
                ent.setIfCreateService(param.getIfCreateService());
                ent.setIsExportExcel(param.getIsExportExcel());
                ent.setIsTree(param.getIsTree());
                ent.setRecordCount(param.getRecordCount());
                ent.setIsFlowMonitor(param.getIsFlowMonitor());
                ent.setIsSearch(param.getIsSearch());
                ent.setIsAuthControl(param.getIsAuthControl());
                ent.setIsShowDictName(param.getIsShowDictName());
                ent.setColsPerRow(param.getColsPerRow());
                ent.setIsUseValidcode(param.getIsUseValidcode());
                ent.setIsCreateSumrow(param.getIsCreateSumrow());
                ent.setFormTags(param.getFormTags());
                ent.setDbServiceName(param.getDbServiceName());
                this.commTableDefService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.commTableDefService.save(param);
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
    public ResponseResult queryList(CommTableDefParam param ){


        List<CommTableDef> list = null;
        try{
            list = this.commTableDefService.queryList(param);
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
        CommTableDef ent = this.commTableDefService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CommTableDefParam param){
        IPage<CommTableDef> page = this.commTableDefService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
