package org.openjweb.cms.module.params;

import lombok.Data;
import org.openjweb.cms.entity.PortalWebsite;

@Data
public class PortalWebsiteParam extends PortalWebsite {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public PortalWebsiteParam() {
        page = 1;
        pageSize = 20;
    }

}
