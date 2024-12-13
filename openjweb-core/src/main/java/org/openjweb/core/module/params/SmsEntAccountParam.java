package org.openjweb.core.module.params;

import lombok.Data;
import org.openjweb.core.entity.SmsEntAccount;

@Data
public class SmsEntAccountParam extends SmsEntAccount {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public SmsEntAccountParam() {
        page = 1;
        pageSize = 20;
    }

}
