package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：
 */
@Data
@TableName("comm_table_def")
public class CommTableDef   implements java.io.Serializable {
	
	@ApiModelProperty(value ="" )
	
	private Long serialNo;
	
	@ApiModelProperty(value ="" )
	
	private String tableName;
	
	@ApiModelProperty(value ="" )
	
	private String tableNameCn;
	
	@ApiModelProperty(value ="" )
	
	private String clsName;
	
	@ApiModelProperty(value ="" )
	
	private String sysCode;
	
	@ApiModelProperty(value ="" )
	
	private String tableDesc;
	
	@ApiModelProperty(value ="" )
	
	private Long tableSerial;
	
	@ApiModelProperty(value ="" )
	
	private Long tableDocNum;
	
	@ApiModelProperty(value ="" )
	
	private String isCreated;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="" )
	
	private String rowId;
	
	@ApiModelProperty(value ="" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="" )
	
	private String isMasterTable;
	
	@ApiModelProperty(value ="" )
	
	private String ifCreateService;
	
	@ApiModelProperty(value ="" )
	
	private String isExportExcel;
	
	@ApiModelProperty(value ="" )
	
	private String isTree;
	
	@ApiModelProperty(value ="" )
	
	private Long recordCount;
	
	@ApiModelProperty(value ="" )
	
	private String isFlowMonitor;
	
	@ApiModelProperty(value ="" )
	
	private String isSearch;
	
	@ApiModelProperty(value ="" )
	
	private String isAuthControl;
	
	@ApiModelProperty(value ="" )
	
	private String isShowDictName;
	
	@ApiModelProperty(value ="" )
	
	private Long colsPerRow;
	
	@ApiModelProperty(value ="" )
	
	private String isUseValidcode;
	
	@ApiModelProperty(value ="" )
	
	private String isCreateSumrow;
	
	@ApiModelProperty(value ="" )
	
	private String formTags;
	
	@ApiModelProperty(value ="" )
	
	private String dbServiceName;

}