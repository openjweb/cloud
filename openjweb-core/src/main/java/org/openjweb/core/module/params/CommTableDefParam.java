package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.CommTableDef;

@Data
public class CommTableDefParam extends CommTableDef {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CommTableDefParam() {
        page = 1;
        pageSize = 20;
    }

}
