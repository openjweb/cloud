package org.openjweb.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：首页动态变化的图片
 */
@Data
@TableName("portal_dynamic_pic")
public class PortalDynamicPic   implements java.io.Serializable {
	
	@ApiModelProperty(value ="null" )
	
	private String comId;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="null" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="null" )
	
	private String flowStatus;
	
	@ApiModelProperty(value ="null" )
	
	private String flowTransId;
	
	@ApiModelProperty(value ="null" )
	
	private String isMobile;
	
	@ApiModelProperty(value ="null" )
	
	private String isMobileAdv;
	
	@ApiModelProperty(value ="null" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="null" )
	
	private String mobileUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String objName;
	
	@ApiModelProperty(value ="在图片中显示的文字" )
	
	private String picTitle;
	
	@ApiModelProperty(value ="图片对应的URL" )
	
	private String picUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String picUrl2;
	
	@ApiModelProperty(value ="null" )
	
	private String picUrlLink;
	
	@ApiModelProperty(value ="每组图片为一套幻灯" )
	
	private String pigGroupId;
	
	@ApiModelProperty(value ="null" )
	
	private String pkId;
	
	@ApiModelProperty(value ="null" )
	
	private String publishDt;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="null" )
	
	private String rowId;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="null" )
	
	private String storeId;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="null" )
	
	private String websiteCode;

}