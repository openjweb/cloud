package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：直连短信供应商的短信企业帐户
 */
@Data
@TableName("sms_ent_account")
public class SmsEntAccount   implements java.io.Serializable {
	
	@ApiModelProperty(value ="null" )
	
	private Double balanceAmt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="null" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="用途不明" )
	
	private String endName;
	
	@ApiModelProperty(value ="企业用户由短信供应商分配的企业编码" )
	
	private String entCode;
	
	@ApiModelProperty(value ="用途不明" )
	
	private String extCode;
	
	@ApiModelProperty(value ="null" )
	
	private String flowStatus;
	
	@ApiModelProperty(value ="null" )
	
	private String flowTransId;
	
	@ApiModelProperty(value ="缺省使用的短信帐号" )
	
	private String isDefault;
	
	@ApiModelProperty(value ="null" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="null" )
	
	private String objName;
	
	@ApiModelProperty(value ="null" )
	
	private String proxyUrl;
	
	@ApiModelProperty(value ="null" )
	@Version
	private Long recordVersion;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="null" )
	
	private String rowId;
	
	@ApiModelProperty(value ="签名短信发送到达率高" )
	
	private String signWords;
	
	@ApiModelProperty(value ="null" )
	
	private String smsSupCode;
	
	@ApiModelProperty(value ="null" )
	
	private String smsType;
	
	@ApiModelProperty(value ="有的短信商友USERID参数" )
	
	private String smsUserid;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="null" )
	
	private String stCode;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="区分不同供应商下不同的用户" )
	
	private String userCnName;
	
	@ApiModelProperty(value ="由短信运营商分配的短信用户名" )
	
	private String userName;
	
	@ApiModelProperty(value ="发送短信使用的密码" )
	
	private String userPassword;

}