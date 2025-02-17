package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：权限基本信息
 */
@Data
@TableName("comm_auth")
public class CommAuth   implements java.io.Serializable {


	//这个怎么没有自动生成，而是手动增加的,可能是表字段定义表里没有这个表的row_id??

	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="主键" )
	private String rowId;
	
	@ApiModelProperty(value ="针对VUE的always show" )
	
	private String alwaysShow;
	
	@ApiModelProperty(value ="null" )
	
	private String clsCode;
	
	@ApiModelProperty(value ="null" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="null" )
	
	private String isAssignToCom;
	
	@ApiModelProperty(value ="null" )
	
	private String isLayui;
	
	@ApiModelProperty(value ="null" )
	
	private String isVue;
	
	@ApiModelProperty(value ="null" )
	
	private String layuiJump;
	
	@ApiModelProperty(value ="null" )
	
	private String layuiName;
	
	@ApiModelProperty(value ="与权限顺序号分开" )
	
	private Long menuSortNo;
	
	@ApiModelProperty(value ="将菜单url与权限控制url分开" )
	
	private String menuUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String newUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String noKeepAlive;
	
	@ApiModelProperty(value ="null" )
	
	private String picFile;
	
	@ApiModelProperty(value ="仅需指定顶级" )
	
	private String sysCode;
	
	@ApiModelProperty(value ="null" )
	
	private String sysRole;
	
	@ApiModelProperty(value ="null" )
	
	private String vueComponent;
	
	@ApiModelProperty(value ="null" )
	
	private String vueHidden;
	
	@ApiModelProperty(value ="null" )
	
	private String vueIcon;
	
	@ApiModelProperty(value ="null" )
	
	private String vuePath;
	
	@ApiModelProperty(value ="null" )
	
	private String vueRedirect;

	@ApiModelProperty(value ="null" )

	private String authName;

	@ApiModelProperty(value ="null" )

	private String commCode;


}