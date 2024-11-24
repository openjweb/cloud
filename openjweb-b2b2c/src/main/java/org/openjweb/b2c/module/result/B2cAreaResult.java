package org.openjweb.b2c.module.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class B2cAreaResult implements Serializable {
    private String comId;
    private String rowId;
    private String areaName;
}
