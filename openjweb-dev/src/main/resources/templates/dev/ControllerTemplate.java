
package ${basePackage}.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import ${fullClassName};
import ${basePackage}.module.params.${entityClassName}Param;
import ${basePackage}.service.${entityClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "${tableDesc}")
@Slf4j
@RestController
@RequestMapping("/api/${classNameLower}")
public class ${entityClassName}Api {
    @Autowired
    private ${entityClassName}Service ${classNameLower}Service;

    /**
     * 新增记录

     */

    @ApiOperation("保存${tableDesc}")
    @ApiImplicitParams({
           <%
           for(field in fieldList){ %>
           @ApiImplicitParam(name = "${field.fieldName}", value = "${field.fieldNameDesc}", paramType = "query"),
           <%}%>
    })

    @RequestMapping("/save")
    public ResponseResult save( ${entityClassName}Param param){
        try {
            log.info("新增记录调用开始..........");


            this.${classNameLower}Service.save(param);
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
    public ResponseResult saveOrUpdate( ${entityClassName}Param param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            ${entityClassName} ent = this.${classNameLower}Service.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                <%
                for(field in fieldList){%>
                ent.set${field.fieldNameUpper}(param.get${field.fieldNameUpper}());
                <%}%>
                this.${classNameLower}Service.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.${classNameLower}Service.save(param);
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
    public ResponseResult queryList(${entityClassName}Param param ){


        List<${entityClassName}> list = null;
        try{
            list = this.${classNameLower}Service.queryList(param);
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
        ${entityClassName} ent = this.${classNameLower}Service.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(${entityClassName}Param param){
        IPage<${entityClassName}> page = this.${classNameLower}Service.findPage(param);
        return ResponseResult.okResult(page);

    }

}
