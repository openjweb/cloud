package org.openjweb.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：内容管理
 */
@Data
@TableName("cms_info")
public class CmsInfo   implements java.io.Serializable {
	
	@ApiModelProperty(value ="null" )
	
	private String actType;
	
	@ApiModelProperty(value ="null" )
	
	private String bannerPic;
	
	@ApiModelProperty(value ="null" )
	
	private String cateTreeCode;
	
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
	
	private String dateNum;
	
	@ApiModelProperty(value ="null" )
	
	private String deptName;
	
	@ApiModelProperty(value ="null" )
	
	private String deptRowId;
	
	@ApiModelProperty(value ="null" )
	
	private String flowStatus;
	
	@ApiModelProperty(value ="null" )
	
	private String hasSmallImage;
	
	@ApiModelProperty(value ="null" )
	
	private String industName;
	
	@ApiModelProperty(value ="个别信息需要单独的页面样式" )
	
	private String infoJspPage;
	
	@ApiModelProperty(value ="信息作者" )
	
	private String infAuthor;
	
	@ApiModelProperty(value ="点击数" )
	
	private Long infClickCount;
	
	@ApiModelProperty(value ="信息正文" )
	
	private String infContent;
	
	@ApiModelProperty(value ="关键词" )
	
	private String infKeyword;
	
	@ApiModelProperty(value ="null" )
	
	private Long infPicCount;
	
	@ApiModelProperty(value ="信息来源" )
	
	private String infSource;
	
	@ApiModelProperty(value ="信息在首页显示的标题" )
	
	private String infTitle;
	
	@ApiModelProperty(value ="详细信息页面显示的标题" )
	
	private String infTitle2;
	
	@ApiModelProperty(value ="null" )
	
	private String infType;
	
	@ApiModelProperty(value ="URL连接" )
	
	private String infUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String isAddScan;
	
	@ApiModelProperty(value ="是否允许回复或评论" )
	
	private String isAllowFeedback;
	
	@ApiModelProperty(value ="null" )
	
	private String isCateFirst;
	
	@ApiModelProperty(value ="null" )
	
	private String isCreateHtml;
	
	@ApiModelProperty(value ="null" )
	
	private String isEndVideo;
	
	@ApiModelProperty(value ="null" )
	
	private String isHot;
	
	@ApiModelProperty(value ="null" )
	
	private String isJp;
	
	@ApiModelProperty(value ="null" )
	
	private String isPic;
	
	@ApiModelProperty(value ="null" )
	
	private String isRecommend;
	
	@ApiModelProperty(value ="在所属栏目中置顶" )
	
	private String isTop;
	
	@ApiModelProperty(value ="null" )
	
	private String isTrain;
	
	@ApiModelProperty(value ="是否URL连接(URL连接不需要正文)" )
	
	private String isUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String isVideo;
	
	@ApiModelProperty(value ="null" )
	
	private String largeImagePath;
	
	@ApiModelProperty(value ="最后一次同步服务器的时间" )
	
	private String lastSyncDt;
	
	@ApiModelProperty(value ="null" )
	
	private String m3u8Url;
	
	@ApiModelProperty(value ="null" )
	
	private String masterRowId;
	
	@ApiModelProperty(value ="null" )
	
	private String mediaExt;
	
	@ApiModelProperty(value ="null" )
	
	private String mediaId;
	
	@ApiModelProperty(value ="如baidu" )
	
	private String mediaProvider;
	
	@ApiModelProperty(value ="缩略图URL地址" )
	
	private String mediaThumb;
	
	@ApiModelProperty(value ="null" )
	
	private String middleImagePath;
	
	@ApiModelProperty(value ="null" )
	
	private Long mobileSortNo;
	
	@ApiModelProperty(value ="null" )
	
	private String monthNum;
	
	@ApiModelProperty(value ="null" )
	
	private String njContType;
	
	@ApiModelProperty(value ="null" )
	
	private String njExgType;
	
	@ApiModelProperty(value ="null" )
	
	private String njInfSource;
	
	@ApiModelProperty(value ="null" )
	
	private String njSubContType;
	
	@ApiModelProperty(value ="null" )
	
	private String njSubExgType;
	
	@ApiModelProperty(value ="null" )
	
	private String njYear;
	
	@ApiModelProperty(value ="未确定是否使用" )
	
	private String objName;
	
	@ApiModelProperty(value ="null" )
	
	private String otherProvince;
	
	@ApiModelProperty(value ="null" )
	
	private String pkId;
	
	@ApiModelProperty(value ="权重，越小越靠前" )
	
	private Long priorityValue;
	
	@ApiModelProperty(value ="有的信息是与省份关联的" )
	
	private String provinceId;
	
	@ApiModelProperty(value ="信息发布日期" )
	
	private String publishDt;
	
	@ApiModelProperty(value ="根据推广ID生成推广页,如0001" )
	
	private String pubId;
	
	@ApiModelProperty(value ="null" )
	
	private String pubUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String relateRowId;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="null" )
	
	private String rowId;
	
	@ApiModelProperty(value ="null" )
	
	private String slideImagePath;
	
	@ApiModelProperty(value ="null" )
	
	private String smallImagePath;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="null" )
	
	private String sourceUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String subTitle;
	
	@ApiModelProperty(value ="null" )
	
	private String subZtUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String teacherDesc;
	
	@ApiModelProperty(value ="null" )
	
	private String teacherName;
	
	@ApiModelProperty(value ="null" )
	
	private String teacherPhoto;
	
	@ApiModelProperty(value ="null" )
	
	private String trainVideoType;
	
	@ApiModelProperty(value ="为集成商品搜索，为商品增加单价字段" )
	
	private Double unitPrice;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="null" )
	
	private Long videoCount;
	
	@ApiModelProperty(value ="null" )
	
	private String videoSourcePath;
	
	@ApiModelProperty(value ="null" )
	
	private String videoUrl;
	
	@ApiModelProperty(value ="null" )
	
	private Long wordCount;
	
	@ApiModelProperty(value ="null" )
	
	private String zhuantiTree;
	
	@ApiModelProperty(value ="null" )
	
	private String ztPicTreeCode;

}