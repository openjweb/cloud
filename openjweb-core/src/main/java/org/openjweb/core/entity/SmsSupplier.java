package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：短信供应商表
 */
@Data
@TableName("sms_supplier")
public class SmsSupplier   implements java.io.Serializable {
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="唯一ID" )
	
	private String rowId;
	
	@ApiModelProperty(value ="记录顺序" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="创建日期" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="修改日期" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="创建人" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="修改人" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="状态位" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="备注" )
	
	private String objName;
	
	@ApiModelProperty(value ="父表ID" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="事务ID" )
	
	private String flowTransId;
	
	@ApiModelProperty(value ="流程状态" )
	
	private String flowStatus;
	
	@ApiModelProperty(value ="乐观锁" )
	@Version
	private Long recordVersion;
	
	@ApiModelProperty(value ="是否默认" )
	
	private String isDefault;
	
	@ApiModelProperty(value ="短信商名称" )
	
	private String supName;
	
	@ApiModelProperty(value ="接收消息URL" )
	
	private String recvMsgUrl;
	
	@ApiModelProperty(value ="查询余额URL" )
	
	private String queryBalanceUrl;
	
	@ApiModelProperty(value ="修改密码URL" )
	
	private String chgPassUrl;
	
	@ApiModelProperty(value ="发送短信URL" )
	
	private String sendMessUrl;
	
	@ApiModelProperty(value ="短信商简称" )
	
	private String abbrName;
	
	@ApiModelProperty(value ="接口地址" )
	
	private String interfaceAddr;
	
	@ApiModelProperty(value ="短信商编码" )
	
	private String supCode;
	
	@ApiModelProperty(value ="省份" )
	
	private String provinceId;
	
	@ApiModelProperty(value ="主页" )
	
	private String homePage;
	
	@ApiModelProperty(value ="注册子账号URL" )
	
	private String registChildUrl;
	
	@ApiModelProperty(value ="接口字符集" )
	
	private String encodingType;
	
	@ApiModelProperty(value ="敏感词URL" )
	
	private String filterWordsUrl;
	
	@ApiModelProperty(value ="收短信URL" )
	
	private String recvSmsUrl;
	
	@ApiModelProperty(value ="错误信息URL" )
	
	private String errorUrl;

}