package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.CommColumnDef;

@Data
public class CommColumnDefParam extends CommColumnDef {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CommColumnDefParam() {
        page = 1;
        pageSize = 20;
    }

}
