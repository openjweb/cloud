package org.openjweb.b2c.module.result;


import lombok.Data;
import org.openjweb.b2c.entity.B2cStore;

@Data

public class B2cStoreResult extends B2cStore {
    private String comId;
    private String rowId;
    private String stName;
}
