package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comm_api_key")
public class CommApiKey   implements java.io.Serializable {
    // Fields
    private String rowId;
    private Long sortNo;
    private String createDt;
    private String updateDt;
    private String createUid;
    private String updateUid;
    private String dataFlg;
    private String objName;
    private String masterRowId;
    private String flowTransId;
    private String flowStatus;
    private Long recordVersion;
    private String apiCom;
    private String comId;
    private String appId;
    private String appSecret;
    private String serviceUrl;
    private String apiOrgNo;
    private String desPubKey;
    private String stCode;
    private String terminalSn;
    private String terminalKey;
    private String deviceId;
    private String subAppId;
    private String clientSn;
    private String apiType;
    private String serverStCode;

}