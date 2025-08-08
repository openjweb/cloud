package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.CommSystemParms;

@Data
public class CommSystemParmsParam extends CommSystemParms {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数
    private Integer pageNo=1; //为兼容以前版本

    public CommSystemParmsParam() {
        page = 1;
        pageSize = 20;
        pageNo = 1;//匹配以前版本
    }

}
