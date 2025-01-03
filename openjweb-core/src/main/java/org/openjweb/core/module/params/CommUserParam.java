package org.openjweb.core.module.params;

import org.openjweb.core.entity.CommUser;

public class CommUserParam extends CommUser {

    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CommUserParam() {
        page = 1;
        pageSize = 20;
    }
}
