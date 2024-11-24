package org.openjweb.b2c.api;

import org.openjweb.b2c.module.params.B2cStoreParam;
import org.openjweb.b2c.service.B2cStoreService;
import org.openjweb.common.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/store/")
public class B2cStoreApi {
    @Resource
    private B2cStoreService b2cStoreService;
    //localhost:8001/api/store/list?comId=C0001

    @RequestMapping("list")

    public ResponseResult getStore(B2cStoreParam param){
        return ResponseResult.okResult(this.b2cStoreService.getB2cStore(param));
    }
}
