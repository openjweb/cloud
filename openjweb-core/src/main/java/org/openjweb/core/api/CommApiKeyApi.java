package org.openjweb.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.openjweb.core.service.CommApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
     * 测试：http://localhost:8001/demo/apikey/query?comId=
 */
@Slf4j
@RestController
@RequestMapping("/demo/apikey")
public class CommApiKeyApi {
    @Autowired
    private CommApiKeyService commApiKeyService;

    /**
     * 新增记录

     */

    @RequestMapping("/save")
    public ResponseResult save( CommApiKeyParam param){
        try {
            log.info("新增记录调用开始..........");
            //http://localhost:8001/demo/apikey/save?appId=aaaa&recordVersion=2

            this.commApiKeyService.save(param);
            log.info("新增记录调用结束..........");
            return ResponseResult.okResult(0,"新增成功!");
        }
        catch(Exception  ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"新增失败!");
        }

    }

    //http://localhost:8001/demo/apikey/saveOrUpdate?rowId=b8925697762bb91aa99fa1f2bdbe44bd&apiCom=TX

    /**
     * 新增或修改记录
     * @param param
     * @return
     */

    @RequestMapping("saveOrUpdate")
    public ResponseResult saveOrUpdate( CommApiKeyParam param){
        if(param==null){
            return ResponseResult.errorResult(-1,"未传入参数.....");
        }
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            CommApiKey ent = this.commApiKeyService.queryByRowId(param.getRowId());
            if(ent!=null) {
                //复制修改的参数
                ent.setApiCom(param.getApiCom());
                ent.setAppId(param.getAppId());
                ent.setApiCom(param.getApiCom());
                this.commApiKeyService.updateById(ent);
            }
            else{
                log.info("没找到实体类..,新增...");
                if(param.getSortNo()==null)param.setSortNo(0L);
                this.commApiKeyService.save(param);
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
    public ResponseResult queryList(CommApiKeyParam param ){


        List<CommApiKey> list = null;
        try{
            list = this.commApiKeyService.queryList(param);
        }
        catch(Exception ex){
            log.error("异常：：：："+ex.toString());
            return ResponseResult.errorResult(-1,ex.toString());
        }

        return ResponseResult.okResult(list);

    }

    //http://localhost:8001/demo/apikey/edit?rowId=1

    /**
     * 查询单条记录
     * @param rowId
     * @return
     */
    @RequestMapping("edit")

    public ResponseResult edit(String rowId){
        CommApiKey ent = this.commApiKeyService.queryByRowId(rowId);
        return ResponseResult.okResult(ent);

    }

    //http://localhost:8001/demo/apikey/findPage

    /**
     * 分页查询
     * @param param
     * @return
     */

    @RequestMapping("findPage")

    public ResponseResult findPage(CommApiKeyParam param){
        IPage<CommApiKey> page = this.commApiKeyService.findPage(param);
        return ResponseResult.okResult(page);

    }





}
