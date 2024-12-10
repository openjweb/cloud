package org.openjweb.dev.entity;

import lombok.Data;

@Data
public class TableColumnInfo {
    private String columnName ;
    private String columnDesc;
    private String columnType;
    private int dataSize ;
    private  int digits;
    private int isNull;


}
