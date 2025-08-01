package org.openjweb.cms.module.params;

import lombok.Data;
import org.openjweb.cms.entity.PortalWebsite;

@Data
public class PortalWebsiteParam extends PortalWebsite {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数
    private Integer pageNo = 1;

    public PortalWebsiteParam() {
        page = 1;
        pageNo = 1;//匹配以前版本
        pageSize = 20;
    }

}
