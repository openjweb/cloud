package org.openjweb.b2c.module.params;


import lombok.Data;
import org.openjweb.b2c.entity.B2cStore;

/**
 * B2cStoreParam.java
 * 店铺基本信息参数类，用于从前端传入查询参数或者
 * @author 王保政
 * @version 1.0.0
 */
@Data
public class B2cStoreParam extends B2cStore {
    private Integer page=1; //当前页
    private Integer pageSize=10;//每页行数

    public B2cStoreParam() {
        page = 1;
        pageSize = 10;
    }

}
