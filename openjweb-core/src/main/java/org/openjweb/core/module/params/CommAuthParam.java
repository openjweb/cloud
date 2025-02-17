package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.CommAuth;

@Data
public class CommAuthParam extends CommAuth {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CommAuthParam() {
        page = 1;
        pageSize = 20;
    }

}
