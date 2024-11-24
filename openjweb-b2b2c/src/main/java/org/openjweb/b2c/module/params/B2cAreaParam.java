package org.openjweb.b2c.module.params;

import lombok.Data;
import org.openjweb.b2c.entity.B2cArea;

/**
 * 实体类B2cArea的参数类,用于向后端传递查询参数或者表单内容
 * @author 王保政
 * @Version 1.0.1
 *
 *
 */
@Data
public class B2cAreaParam extends B2cArea {

    private Integer page=1; //当前页
    private Integer pageSize=10;//每页行数

    public B2cAreaParam() {
        page = 1;
        pageSize = 10;
    }
}
