package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.SmsSupplier;

@Data
public class SmsSupplierParam extends SmsSupplier {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public SmsSupplierParam() {
        page = 1;
        pageSize = 20;
    }

}
