package org.openjweb.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：网站栏目基本信息
 */
@Data
@TableName("cms_category")
public class CmsCategory   implements java.io.Serializable {
	
	@ApiModelProperty(value ="栏目编码" )
	
	private String cateCode;
	
	@ApiModelProperty(value ="栏目说明" )
	
	private String cateDesc;
	
	@ApiModelProperty(value ="null" )
	
	private String cateDomainName;
	
	@ApiModelProperty(value ="null" )
	
	private String cateDynamicUrl;
	
	@ApiModelProperty(value ="栏目名称" )
	
	private String cateName;
	
	@ApiModelProperty(value ="如此栏目需要附带一个图片" )
	
	private String catePicPath;
	
	@ApiModelProperty(value ="为提高访问效率，如果使用静态网页，则使用此字段" )
	
	private String cateSaticUrl;
	
	@ApiModelProperty(value ="栏目类型" )
	
	private String cateType;
	
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
	
	private String infoJspTemplate;
	
	@ApiModelProperty(value ="是否权限控制，改为搜索根栏目，" )
	
	private String isAuthControl;
	
	@ApiModelProperty(value ="null" )
	
	private String isHttps;
	
	@ApiModelProperty(value ="标识是显示信息列表Y还是单条信息N" )
	
	private String isInfoContainer;
	
	@ApiModelProperty(value ="如果是，则网站地图页面显示此栏目" )
	
	private String isInSitemap;
	
	@ApiModelProperty(value ="是否在首页的全文检索栏目下拉中显示" )
	
	private String isLuceneSearch;
	
	@ApiModelProperty(value ="是否门户页面的导航条中的一个连接" )
	
	private String isNavigatorMenu;
	
	@ApiModelProperty(value ="null" )
	
	private String isPrepubJms;
	
	@ApiModelProperty(value ="null" )
	
	private String isPubJms;
	
	@ApiModelProperty(value ="null" )
	
	private String isPubRss;
	
	@ApiModelProperty(value ="null" )
	
	private String langType;
	
	@ApiModelProperty(value ="null" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="一般静态页根目录下的信息发布目录" )
	
	private String nginxProxyDir;
	
	@ApiModelProperty(value ="null" )
	
	private String objName;
	
	@ApiModelProperty(value ="null" )
	
	private String pkId;
	
	@ApiModelProperty(value ="发布静态页不同网站放不同路径" )
	
	private String rootSitePath;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="null" )
	
	private String rowId;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="栏目停用时间" )
	
	private String stopDt;
	
	@ApiModelProperty(value ="null" )
	
	private String treenodeIcon;
	
	@ApiModelProperty(value ="null" )
	
	private String treeCode;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;

}