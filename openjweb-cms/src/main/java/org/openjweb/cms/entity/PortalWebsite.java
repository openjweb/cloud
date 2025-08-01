package org.openjweb.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

/**
 * 功能说明：
 */
@Data
@TableName("portal_website")
public class PortalWebsite   implements java.io.Serializable {

	@TableId(type = IdType.ASSIGN_UUID)
	//@TableField(value = "row_id", jdbcType = JdbcType.VARCHAR,insertStrategy = FieldStrategy.IGNORED)
	@ApiModelProperty(value ="null" )
    private String rowId;

	
	@ApiModelProperty(value ="本网站的关于我们的信息" )
	
	private String aboutusMsg;
	
	@ApiModelProperty(value ="地址信息" )
	
	private String addressMsg;
	
	@ApiModelProperty(value ="本网站的管理员帐号" )
	
	private String adminLoginId;
	
	@ApiModelProperty(value ="网站所属单位,如C0001" )
	
	private String comId;
	
	@ApiModelProperty(value ="可能同时包含google地图" )
	
	private String contactUs;
	
	@ApiModelProperty(value ="版权信息" )
	
	private String copyrightMsg;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="null" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="如index.jsp" )
	
	private String defaultPage;
	
	@ApiModelProperty(value ="如www.xxx.com" )
	
	private String domainName;
	
	@ApiModelProperty(value ="null" )
	
	private String emailAddress;
	
	@ApiModelProperty(value ="null" )
	
	private String flowStatus;
	
	@ApiModelProperty(value ="null" )
	
	private String flowTransId;
	
	@ApiModelProperty(value ="发布信息时，不允许带以下敏感词" )
	
	private String forbidWords;
	
	@ApiModelProperty(value ="有的网站不隶属单位" )
	
	private String fullName;
	
	@ApiModelProperty(value ="如京ICP备05083456号" )
	
	private String icpNo;
	
	@ApiModelProperty(value ="null" )
	
	private String infoSource;
	
	@ApiModelProperty(value ="如/portal/wentong/xxx.jsp" )
	
	private String infListPage;
	
	@ApiModelProperty(value ="如果根据IP访问，则查找一个域名作为主站" )
	
	private String isMainSite;
	
	@ApiModelProperty(value ="null" )
	
	private String isToBackend;
	
	@ApiModelProperty(value ="null" )
	
	private String lawDesc;
	
	@ApiModelProperty(value ="null" )
	
	private String loginUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="null" )
	
	private String objName;
	
	@ApiModelProperty(value ="null" )
	
	private String pkId;
	
	@ApiModelProperty(value ="null" )
	
	private String postCode;
	
	@ApiModelProperty(value ="相对当前web应用的子目录" )
	
	private String relatePath;


	
	@ApiModelProperty(value ="全文检索搜索列表页模板" )
	
	private String searchListPage;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="展示详细信息的模板" )
	
	private String viewInfoPage;
	
	@ApiModelProperty(value ="网站代号，如文通网wentongPortal，与栏目中所属网站对应" )
	
	private String websiteCode;
	
	@ApiModelProperty(value ="网站的简称" )
	
	private String websiteName;

}