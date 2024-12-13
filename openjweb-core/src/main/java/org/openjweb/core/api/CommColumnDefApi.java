
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.CommColumnDef;
import org.openjweb.core.module.params.CommColumnDefParam;
import org.openjweb.core.service.CommColumnDefService;
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
@RequestMapping("/demo/api/commColumnDef")
public class CommColumnDefApi {
    @Autowired
    private CommColumnDefService commColumnDefService;

    /**
     * 新增记录

     */

    @ApiOperation("保存")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "serialNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "tableSerialNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnNameCn", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnDesc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "clsFieldName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnDatatype", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnLength", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnDigitLen", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isNotNull", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnClsType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isAutoInc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "incStartVal", value = "", paramType = "query"),
           @ApiImplicitParam(name = "incCurrVal", value = "", paramType = "query"),
           @ApiImplicitParam(name = "defaultValCretype", value = "", paramType = "query"),
           @ApiImplicitParam(name = "defaultValFunction", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isQueryCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isPrimaryKey", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isSort", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isSortAsc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortColNum", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataGetType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dateFormatType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "inputType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "jsPopWindows", value = "", paramType = "query"),
           @ApiImplicitParam(name = "codeNameSql1", value = "", paramType = "query"),
           @ApiImplicitParam(name = "codeNameSql2", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dictTypeCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "listSortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isListCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isEditCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "editSortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isUnique", value = "", paramType = "query"),
           @ApiImplicitParam(name = "validateFunc", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isNulLocale", value = "", paramType = "query"),
           @ApiImplicitParam(name = "defaultValue", value = "", paramType = "query"),
           @ApiImplicitParam(name = "rowId", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateDt", value = "", paramType = "query"),
           @ApiImplicitParam(name = "createUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "updateUid", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sortNo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "popWin", value = "", paramType = "query"),
           @ApiImplicitParam(name = "labelKo", value = "", paramType = "query"),
           @ApiImplicitParam(name = "labelJp", value = "", paramType = "query"),
           @ApiImplicitParam(name = "labelEn", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isExportExcel", value = "", paramType = "query"),
           @ApiImplicitParam(name = "nameSql", value = "", paramType = "query"),
           @ApiImplicitParam(name = "codeSql", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isExcelKeyCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isAuthControl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isCodeCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isNameCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isTreeSelCol", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isOverDate", value = "", paramType = "query"),
           @ApiImplicitParam(name = "patternValue", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isCustCond", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isSearch", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isLuceneIndex", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isLuceneAnalyzed", value = "", paramType = "query"),
           @ApiImplicitParam(name = "searchFieldType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "listPageWidth", value = "", paramType = "query"),
           @ApiImplicitParam(name = "editColPerrow", value = "", paramType = "query"),
           @ApiImplicitParam(name = "editPageWidth", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isReadonly", value = "", paramType = "query"),
           @ApiImplicitParam(name = "editColJavascript", value = "", paramType = "query"),
           @ApiImplicitParam(name = "queryType", value = "", paramType = "query"),
           @ApiImplicitParam(name = "columnGroup", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CommColumnDefParam param){
        try {
            log.info("新增记录调用开始..........");


            this.commColumnDefService.save(param);
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
    public ResponseResult saveOrUpdate( CommColumnDefParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CommColumnDef ent = this.commColumnDefService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setSerialNo(param.getSerialNo());
                ent.setTableSerialNo(param.getTableSerialNo());
                ent.setColumnName(param.getColumnName());
                ent.setColumnNameCn(param.getColumnNameCn());
                ent.setColumnDesc(param.getColumnDesc());
                ent.setClsFieldName(param.getClsFieldName());
                ent.setColumnDatatype(param.getColumnDatatype());
                ent.setColumnLength(param.getColumnLength());
                ent.setColumnDigitLen(param.getColumnDigitLen());
                ent.setIsNotNull(param.getIsNotNull());
                ent.setColumnClsType(param.getColumnClsType());
                ent.setIsAutoInc(param.getIsAutoInc());
                ent.setIncStartVal(param.getIncStartVal());
                ent.setIncCurrVal(param.getIncCurrVal());
                ent.setDefaultValCretype(param.getDefaultValCretype());
                ent.setDefaultValFunction(param.getDefaultValFunction());
                ent.setIsQueryCol(param.getIsQueryCol());
                ent.setIsPrimaryKey(param.getIsPrimaryKey());
                ent.setIsSort(param.getIsSort());
                ent.setIsSortAsc(param.getIsSortAsc());
                ent.setSortColNum(param.getSortColNum());
                ent.setDataGetType(param.getDataGetType());
                ent.setDateFormatType(param.getDateFormatType());
                ent.setInputType(param.getInputType());
                ent.setJsPopWindows(param.getJsPopWindows());
                ent.setCodeNameSql1(param.getCodeNameSql1());
                ent.setCodeNameSql2(param.getCodeNameSql2());
                ent.setDictTypeCode(param.getDictTypeCode());
                ent.setListSortNo(param.getListSortNo());
                ent.setIsListCol(param.getIsListCol());
                ent.setIsEditCol(param.getIsEditCol());
                ent.setEditSortNo(param.getEditSortNo());
                ent.setIsUnique(param.getIsUnique());
                ent.setValidateFunc(param.getValidateFunc());
                ent.setIsNulLocale(param.getIsNulLocale());
                ent.setDefaultValue(param.getDefaultValue());
                ent.setRowId(param.getRowId());
                ent.setCreateDt(param.getCreateDt());
                ent.setUpdateDt(param.getUpdateDt());
                ent.setCreateUid(param.getCreateUid());
                ent.setUpdateUid(param.getUpdateUid());
                ent.setSortNo(param.getSortNo());
                ent.setDataFlg(param.getDataFlg());
                ent.setPopWin(param.getPopWin());
                ent.setLabelKo(param.getLabelKo());
                ent.setLabelJp(param.getLabelJp());
                ent.setLabelEn(param.getLabelEn());
                ent.setIsExportExcel(param.getIsExportExcel());
                ent.setNameSql(param.getNameSql());
                ent.setCodeSql(param.getCodeSql());
                ent.setIsExcelKeyCol(param.getIsExcelKeyCol());
                ent.setIsAuthControl(param.getIsAuthControl());
                ent.setIsCodeCol(param.getIsCodeCol());
                ent.setIsNameCol(param.getIsNameCol());
                ent.setIsTreeSelCol(param.getIsTreeSelCol());
                ent.setIsOverDate(param.getIsOverDate());
                ent.setPatternValue(param.getPatternValue());
                ent.setIsCustCond(param.getIsCustCond());
                ent.setIsSearch(param.getIsSearch());
                ent.setIsLuceneIndex(param.getIsLuceneIndex());
                ent.setIsLuceneAnalyzed(param.getIsLuceneAnalyzed());
                ent.setSearchFieldType(param.getSearchFieldType());
                ent.setListPageWidth(param.getListPageWidth());
                ent.setEditColPerrow(param.getEditColPerrow());
                ent.setEditPageWidth(param.getEditPageWidth());
                ent.setIsReadonly(param.getIsReadonly());
                ent.setEditColJavascript(param.getEditColJavascript());
                ent.setQueryType(param.getQueryType());
                ent.setColumnGroup(param.getColumnGroup());
                this.commColumnDefService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.commColumnDefService.save(param);
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
    public ResponseResult queryList(CommColumnDefParam param ){


        //http://localhost:8001/demo/api/commColumnDef/query?tableSerialNo=13973
        List<CommColumnDef> list = null;
        try{
            list = this.commColumnDefService.queryList(param);
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
        CommColumnDef ent = this.commColumnDefService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CommColumnDefParam param){
        IPage<CommColumnDef> page = this.commColumnDefService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
