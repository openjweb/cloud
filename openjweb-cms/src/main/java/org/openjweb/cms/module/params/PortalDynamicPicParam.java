package org.openjweb.cms.module.params;

import lombok.Data;
import org.openjweb.cms.entity.PortalDynamicPic;

@Data
public class PortalDynamicPicParam extends PortalDynamicPic {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public PortalDynamicPicParam() {
        page = 1;
        pageSize = 20;
    }

}
