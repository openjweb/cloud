package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：API_KEY实体类
 */
@Data
@TableName("comm_api_key")
public class CommApiKey   implements java.io.Serializable {
    // Fields
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value ="主键" )
    private String rowId;

    @ApiModelProperty(value ="记录顺序" )
    private Long sortNo;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value ="创建日期" )
    private String createDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value ="最后修改日期" )

    private String updateDt;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value ="创建人" )
    private String createUid;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value ="最后修改人" )

    private String updateUid;
    @ApiModelProperty(value ="是否可用" )
    private String dataFlg;

    @ApiModelProperty(value ="备注" )
    private String objName;

    @ApiModelProperty(value ="主表ROWID" )

    private String masterRowId;

    @ApiModelProperty(value ="流程事务ID" )

    private String flowTransId;
    @ApiModelProperty(value ="流程状态" )

    private String flowStatus;

    @ApiModelProperty(value ="乐观锁" )

    private Long recordVersion;
    @ApiModelProperty(value ="接口供应商" )

    private String apiCom;
    @ApiModelProperty(value ="所属单位" )

    private String comId;
    @ApiModelProperty(value ="APPID" )

    private String appId;
    @ApiModelProperty(value ="APP密钥" )

    private String appSecret;

    @ApiModelProperty(value ="API接口地址" )

    private String serviceUrl;
    @ApiModelProperty(value ="接口分配的机构号" )

    private String apiOrgNo;
    @ApiModelProperty(value ="接口DES公钥" )

    private String desPubKey;
    @ApiModelProperty(value ="店铺编码" )

    private String stCode;

    @ApiModelProperty(value ="终端序列号" )

    private String terminalSn;
    @ApiModelProperty(value ="终端密钥" )

    private String terminalKey;
    @ApiModelProperty(value ="设备ID" )

    private String deviceId;
    @ApiModelProperty(value ="子APPID" )

    private String subAppId;
    @ApiModelProperty(value ="客户端序列号" )

    private String clientSn;
    @ApiModelProperty(value ="接口类型" )

    private String apiType;

    @ApiModelProperty(value ="服务器端接口编码" )

    private String serverStCode;

}