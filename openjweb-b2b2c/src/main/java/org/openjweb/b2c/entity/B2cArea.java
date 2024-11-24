package org.openjweb.b2c.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("b2c_area")
public class B2cArea implements Serializable {
    //销售地区实体类
    // Fields
    //@Id ---这个删除的时候没反应
    @TableId(type = IdType.ASSIGN_UUID)
    private String rowId;
    private Long sortNo;

    @TableField(fill = FieldFill.INSERT)
    private String createDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateDt;

    @TableField(fill = FieldFill.INSERT)
    private String createUid;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUid;
    private String dataFlg;
    private String objName;
    private String masterRowId;
    private String flowTransId;
    private String treeCode;

    @Version
    private Long recordVersion = 0L;//因为是非空，所以试着给一个初始值
    private String flowStatus;
    private String areaName;
    private String comId;
    private String headerName;
    private String areaCode;
    private String headerLoginId;

    private String logoPath;

}
