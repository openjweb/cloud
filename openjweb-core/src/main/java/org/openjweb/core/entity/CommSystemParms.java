package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：
 */
@Data
@TableName("comm_system_parms")
public class CommSystemParms   implements java.io.Serializable {
	
	@ApiModelProperty(value ="null" )
	
	private String parmName;
	
	@ApiModelProperty(value ="null" )
	
	private String parmValue;
	
	@ApiModelProperty(value ="null" )
	
	private String parmDesc;
	
	@ApiModelProperty(value ="存储此参数项的其他参数值，以便实施过程中根据不同项目配置" )
	
	private String parmOldValues;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="null" )
	
	private String rowId;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="null" )
	
	private String pkId;
	
	@ApiModelProperty(value ="null" )
	
	private String objName;
	
	@ApiModelProperty(value ="null" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="null" )
	
	private String flowTransId;
	
	@ApiModelProperty(value ="null" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="null" )
	
	private String flowStatus;

}