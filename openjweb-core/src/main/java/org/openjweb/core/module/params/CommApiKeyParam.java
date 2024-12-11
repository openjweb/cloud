package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.CommApiKey;

@Data
public class CommApiKeyParam extends CommApiKey {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public CommApiKeyParam() {
        page = 1;
        pageSize = 20;
    }

}
