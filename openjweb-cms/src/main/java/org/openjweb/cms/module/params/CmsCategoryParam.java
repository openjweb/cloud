package org.openjweb.cms.module.params;

import lombok.Data;
import org.openjweb.cms.entity.CmsCategory;

@Data
public class CmsCategoryParam extends CmsCategory {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CmsCategoryParam() {
        page = 1;
        pageSize = 20;
    }

}
