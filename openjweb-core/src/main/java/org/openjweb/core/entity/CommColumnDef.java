package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：
 */
@Data
@TableName("comm_column_def")
public class CommColumnDef   implements java.io.Serializable {
	
	@ApiModelProperty(value ="" )
	
	private Long serialNo;
	
	@ApiModelProperty(value ="" )
	
	private Long tableSerialNo;
	
	@ApiModelProperty(value ="" )
	
	private String columnName;
	
	@ApiModelProperty(value ="" )
	
	private String columnNameCn;
	
	@ApiModelProperty(value ="" )
	
	private String columnDesc;
	
	@ApiModelProperty(value ="" )
	
	private String clsFieldName;
	
	@ApiModelProperty(value ="" )
	
	private String columnDatatype;
	
	@ApiModelProperty(value ="" )
	
	private Long columnLength;
	
	@ApiModelProperty(value ="" )
	
	private Long columnDigitLen;
	
	@ApiModelProperty(value ="" )
	
	private String isNotNull;
	
	@ApiModelProperty(value ="" )
	
	private String columnClsType;
	
	@ApiModelProperty(value ="" )
	
	private String isAutoInc;
	
	@ApiModelProperty(value ="" )
	
	private Long incStartVal;
	
	@ApiModelProperty(value ="" )
	
	private Long incCurrVal;
	
	@ApiModelProperty(value ="" )
	
	private String defaultValCretype;
	
	@ApiModelProperty(value ="" )
	
	private String defaultValFunction;
	
	@ApiModelProperty(value ="" )
	
	private String isQueryCol;
	
	@ApiModelProperty(value ="" )
	
	private String isPrimaryKey;
	
	@ApiModelProperty(value ="" )
	
	private String isSort;
	
	@ApiModelProperty(value ="" )
	
	private String isSortAsc;
	
	@ApiModelProperty(value ="" )
	
	private Long sortColNum;
	
	@ApiModelProperty(value ="" )
	
	private String dataGetType;
	
	@ApiModelProperty(value ="" )
	
	private String dateFormatType;
	
	@ApiModelProperty(value ="" )
	
	private String inputType;
	
	@ApiModelProperty(value ="" )
	
	private String jsPopWindows;
	
	@ApiModelProperty(value ="" )
	
	private String codeNameSql1;
	
	@ApiModelProperty(value ="" )
	
	private String codeNameSql2;
	
	@ApiModelProperty(value ="" )
	
	private String dictTypeCode;
	
	@ApiModelProperty(value ="" )
	
	private Long listSortNo;
	
	@ApiModelProperty(value ="" )
	
	private String isListCol;
	
	@ApiModelProperty(value ="" )
	
	private String isEditCol;
	
	@ApiModelProperty(value ="" )
	
	private Long editSortNo;
	
	@ApiModelProperty(value ="" )
	
	private String isUnique;
	
	@ApiModelProperty(value ="" )
	
	private String validateFunc;
	
	@ApiModelProperty(value ="" )
	
	private String isNulLocale;
	
	@ApiModelProperty(value ="" )
	
	private String defaultValue;
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
	
	private String popWin;
	
	@ApiModelProperty(value ="" )
	
	private String labelKo;
	
	@ApiModelProperty(value ="" )
	
	private String labelJp;
	
	@ApiModelProperty(value ="" )
	
	private String labelEn;
	
	@ApiModelProperty(value ="" )
	
	private String isExportExcel;
	
	@ApiModelProperty(value ="" )
	
	private String nameSql;
	
	@ApiModelProperty(value ="" )
	
	private String codeSql;
	
	@ApiModelProperty(value ="" )
	
	private String isExcelKeyCol;
	
	@ApiModelProperty(value ="" )
	
	private String isAuthControl;
	
	@ApiModelProperty(value ="" )
	
	private String isCodeCol;
	
	@ApiModelProperty(value ="" )
	
	private String isNameCol;
	
	@ApiModelProperty(value ="" )
	
	private String isTreeSelCol;
	
	@ApiModelProperty(value ="" )
	
	private String isOverDate;
	
	@ApiModelProperty(value ="" )
	
	private String patternValue;
	
	@ApiModelProperty(value ="" )
	
	private String isCustCond;
	
	@ApiModelProperty(value ="" )
	
	private String isSearch;
	
	@ApiModelProperty(value ="" )
	
	private String isLuceneIndex;
	
	@ApiModelProperty(value ="" )
	
	private String isLuceneAnalyzed;
	
	@ApiModelProperty(value ="" )
	
	private String searchFieldType;
	
	@ApiModelProperty(value ="" )
	
	private Long listPageWidth;
	
	@ApiModelProperty(value ="" )
	
	private Long editColPerrow;
	
	@ApiModelProperty(value ="" )
	
	private Long editPageWidth;
	
	@ApiModelProperty(value ="" )
	
	private String isReadonly;
	
	@ApiModelProperty(value ="" )
	
	private String editColJavascript;
	
	@ApiModelProperty(value ="" )
	
	private String queryType;
	
	@ApiModelProperty(value ="" )
	
	private Long columnGroup;

}