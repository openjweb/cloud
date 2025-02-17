
package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.CommAuth;
import org.openjweb.core.module.params.CommAuthParam;
import org.openjweb.core.service.CommAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试：
 */
@Api(tags = "权限基本信息")
@Slf4j
@RestController
@RequestMapping("api/comm/auth")
public class CommAuthApi {
    @Autowired
    private CommAuthService commAuthService;

    /**
     * 新增记录

     */

    @ApiOperation("保存权限基本信息")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "alwaysShow", value = "针对VUE的always show", paramType = "query"),
           @ApiImplicitParam(name = "clsCode", value = "", paramType = "query"),
           @ApiImplicitParam(name = "dataFlg", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isAssignToCom", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isLayui", value = "", paramType = "query"),
           @ApiImplicitParam(name = "isVue", value = "", paramType = "query"),
           @ApiImplicitParam(name = "layuiJump", value = "", paramType = "query"),
           @ApiImplicitParam(name = "layuiName", value = "", paramType = "query"),
           @ApiImplicitParam(name = "menuSortNo", value = "与权限顺序号分开", paramType = "query"),
           @ApiImplicitParam(name = "menuUrl", value = "将菜单url与权限控制url分开", paramType = "query"),
           @ApiImplicitParam(name = "newUrl", value = "", paramType = "query"),
           @ApiImplicitParam(name = "noKeepAlive", value = "", paramType = "query"),
           @ApiImplicitParam(name = "picFile", value = "", paramType = "query"),
           @ApiImplicitParam(name = "sysCode", value = "仅需指定顶级", paramType = "query"),
           @ApiImplicitParam(name = "sysRole", value = "", paramType = "query"),
           @ApiImplicitParam(name = "vueComponent", value = "", paramType = "query"),
           @ApiImplicitParam(name = "vueHidden", value = "", paramType = "query"),
           @ApiImplicitParam(name = "vueIcon", value = "", paramType = "query"),
           @ApiImplicitParam(name = "vuePath", value = "", paramType = "query"),
           @ApiImplicitParam(name = "vueRedirect", value = "", paramType = "query"),
    })

    @RequestMapping("/save")
    public ResponseResult save( CommAuthParam param){
        try {
            log.info("新增记录调用开始..........");


            this.commAuthService.save(param);
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
    public ResponseResult saveOrUpdate( CommAuthParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CommAuth ent = this.commAuthService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数

                ent.setAlwaysShow(param.getAlwaysShow());
                ent.setClsCode(param.getClsCode());
                ent.setDataFlg(param.getDataFlg());
                ent.setIsAssignToCom(param.getIsAssignToCom());
                ent.setIsLayui(param.getIsLayui());
                ent.setIsVue(param.getIsVue());
                ent.setLayuiJump(param.getLayuiJump());
                ent.setLayuiName(param.getLayuiName());
                ent.setMenuSortNo(param.getMenuSortNo());
                ent.setMenuUrl(param.getMenuUrl());
                ent.setNewUrl(param.getNewUrl());
                ent.setNoKeepAlive(param.getNoKeepAlive());
                ent.setPicFile(param.getPicFile());
                ent.setSysCode(param.getSysCode());
                ent.setSysRole(param.getSysRole());
                ent.setVueComponent(param.getVueComponent());
                ent.setVueHidden(param.getVueHidden());
                ent.setVueIcon(param.getVueIcon());
                ent.setVuePath(param.getVuePath());
                ent.setVueRedirect(param.getVueRedirect());
                this.commAuthService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");

                this.commAuthService.save(param);
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
    public ResponseResult queryList(CommAuthParam param ){


        List<CommAuth> list = null;
        try{
            list = this.commAuthService.queryList(param);
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
        CommAuth ent = this.commAuthService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CommAuthParam param){
        IPage<CommAuth> page = this.commAuthService.findPage(param);
        return ResponseResult.okResult(page);

    }

}
