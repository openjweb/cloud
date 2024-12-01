package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.io.Serializable;

@Slf4j
@Data
@TableName("comm_sso_client_app")
public class CommSsoClientApp implements  Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String rowId;
    private String dataFlg;
    private Integer refreshTokenOverdueSeconds;
    private String account;
    private String password;
    private String clientName;
    private Integer accessTokenOverdueSeconds;
    private Long clientId;
    private String callBackUrl;
    private Long sortNo;

    @TableField(fill = FieldFill.INSERT)
    private String createDt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateDt;
    @TableField(fill = FieldFill.INSERT)
    private String createUid;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUid;
    @Version
    private Long recordVersion = 0L;//因为是非空，所以试着给一个初始值

}
