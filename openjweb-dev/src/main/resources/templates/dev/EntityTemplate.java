package ${packageName};

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：${tableDesc}
 */
@Data
@TableName("${tableName}")
public class ${entityClassName}   implements java.io.Serializable {
    <%
	for(field in fieldList){
	%>
	${field.tableId}
	${field.apiProperty}
	${field.defaultValueExpr}
	${field.fieldDeclare}
    <%}%>

}