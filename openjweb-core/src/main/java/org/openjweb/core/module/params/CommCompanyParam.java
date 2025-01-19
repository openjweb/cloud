package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.CommCompany;

@Data
public class CommCompanyParam extends CommCompany {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CommCompanyParam() {
        page = 1;
        pageSize = 20;
    }

}
