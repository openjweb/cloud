package org.openjweb.cms.module.params;

import lombok.Data;
import org.openjweb.cms.entity.CmsInfo;

@Data
public class CmsInfoParam extends CmsInfo {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CmsInfoParam() {
        page = 1;
        pageSize = 20;
    }

}
