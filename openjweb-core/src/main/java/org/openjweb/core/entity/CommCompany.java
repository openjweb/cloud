package org.openjweb.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能说明：公司基本信息表
 */
@Data
@TableName("comm_company")
public class CommCompany   implements java.io.Serializable {
	
	@ApiModelProperty(value ="null" )
	
	private String appSecret;
	
	@ApiModelProperty(value ="null" )
	
	private String areaName;
	
	@ApiModelProperty(value ="null" )
	
	private String backLogo;
	
	@ApiModelProperty(value ="null" )
	
	private String bankAcctNo;
	
	@ApiModelProperty(value ="null" )
	
	private String bankAddr;
	
	@ApiModelProperty(value ="在银行开户的公司名称" )
	
	private String bankComName;
	
	@ApiModelProperty(value ="开户银行详细地址" )
	
	private String bankName;
	
	@ApiModelProperty(value ="null" )
	
	private String bankType;
	
	@ApiModelProperty(value ="null" )
	
	private String beianNo;
	
	@ApiModelProperty(value ="null" )
	
	private String bizDesc;
	
	@ApiModelProperty(value ="null" )
	
	private String bizDesc2;
	
	@ApiModelProperty(value ="null" )
	
	private String bizType;
	
	@ApiModelProperty(value ="null" )
	
	private String buildDt;
	
	@ApiModelProperty(value ="null" )
	
	private String businessLiscenseNo;
	
	@ApiModelProperty(value ="null" )
	
	private String businessLiscensePic;
	
	@ApiModelProperty(value ="null" )
	
	private String channelLoginId;
	
	@ApiModelProperty(value ="null" )
	
	private String checkDt;
	
	@ApiModelProperty(value ="null" )
	
	private String checkPsn;
	
	@ApiModelProperty(value ="null" )
	
	private String comAddr;
	
	@ApiModelProperty(value ="null" )
	
	private String comAttr;
	
	@ApiModelProperty(value ="成立时间" )
	
	private String comBuildDt;
	
	@ApiModelProperty(value ="公司备注" )
	
	private String comDesc;
	
	@ApiModelProperty(value ="null" )
	
	private String comFax;
	
	@ApiModelProperty(value ="null" )
	
	private String comGuimo;
	
	@ApiModelProperty(value ="null" )
	
	private String comIdea;
	
	@ApiModelProperty(value ="null" )
	
	private String comIntroduce;
	
	@ApiModelProperty(value ="null" )
	
	private String comIntroduce2;
	
	@ApiModelProperty(value ="null" )
	
	private String comLayoutPic;
	
	@ApiModelProperty(value ="单位名称" )
	
	private String comName;
	
	@ApiModelProperty(value ="null" )
	
	private Long comPsnNum;
	
	@ApiModelProperty(value ="null" )
	
	private String comTel;
	
	@ApiModelProperty(value ="公司类型" )
	
	private String comType;
	
	@ApiModelProperty(value ="null" )
	
	private Double comXpos;
	
	@ApiModelProperty(value ="null" )
	
	private Double comYpos;
	
	@ApiModelProperty(value ="null" )
	
	private String contactMobile;
	
	@ApiModelProperty(value ="null" )
	
	private String contactName;
	
	@ApiModelProperty(value ="null" )
	
	private String contactQq;
	
	@ApiModelProperty(value ="null" )
	
	private String contactTel;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT)
	private String createUid;
	
	@ApiModelProperty(value ="null" )
	
	private String creditDesc;
	
	@ApiModelProperty(value ="通淘宝信誉等级" )
	
	private String creditLevel;
	
	@ApiModelProperty(value ="null" )
	
	private String dataFlg;
	
	@ApiModelProperty(value ="null" )
	
	private String dealLvl;
	
	@ApiModelProperty(value ="null" )
	
	private String defaultTitle;
	
	@ApiModelProperty(value ="null" )
	
	private String domainName;
	
	@ApiModelProperty(value ="null" )
	
	private String emailAddr;
	
	@ApiModelProperty(value ="微商入驻协议" )
	
	private String feeDesc;
	
	@ApiModelProperty(value ="null" )
	
	private String financeContact;
	
	@ApiModelProperty(value ="null" )
	
	private String financeTel;
	
	@ApiModelProperty(value ="财务的微信ID" )
	
	private String finOpenId;
	
	@ApiModelProperty(value ="null" )
	
	private String healthLiscensePic;
	
	@ApiModelProperty(value ="null" )
	
	private String icbcAcctName;
	
	@ApiModelProperty(value ="null" )
	
	private String icbcAcctNo;
	
	@ApiModelProperty(value ="null" )
	
	private String isBatPur;
	
	@ApiModelProperty(value ="作为家政频道" )
	
	private String isFirstChannel;
	
	@ApiModelProperty(value ="null" )
	
	private String isHasCommission;
	
	@ApiModelProperty(value ="初始化会生成默认的公司部门和管理员" )
	
	private String isInit;
	
	@ApiModelProperty(value ="是否锁定，锁定则不允许此公司的人员登录" )
	
	private String isLock;
	
	@ApiModelProperty(value ="null" )
	
	private String isMember;
	
	@ApiModelProperty(value ="null" )
	
	private String isNeedBuy;
	
	@ApiModelProperty(value ="null" )
	
	private String isO2o;
	
	@ApiModelProperty(value ="null" )
	
	private String isOrderCommission;
	
	@ApiModelProperty(value ="null" )
	
	private String isPassed;
	
	@ApiModelProperty(value ="null" )
	
	private String isPassEnt;
	
	@ApiModelProperty(value ="null" )
	
	private String isRecommend;
	
	@ApiModelProperty(value ="作为商城频道" )
	
	private String isSecondChannel;
	
	@ApiModelProperty(value ="作为生活娱乐频道" )
	
	private String isThirdChannel;
	
	@ApiModelProperty(value ="null" )
	
	private String isVipSup;
	
	@ApiModelProperty(value ="null" )
	
	private String isXiaobao;
	
	@ApiModelProperty(value ="null" )
	
	private String jyType;
	
	@ApiModelProperty(value ="null" )
	
	private String keyWord;
	
	@ApiModelProperty(value ="null" )
	
	private String lawPsnId;
	
	@ApiModelProperty(value ="null" )
	
	private String lawTel;
	
	@ApiModelProperty(value ="null" )
	
	private String lawTelDistrictCode;
	
	@ApiModelProperty(value ="null" )
	
	private String legalPerson;
	
	@ApiModelProperty(value ="null" )
	
	private String localUrl;
	
	@ApiModelProperty(value ="null" )
	
	private String loginBanner;
	
	@ApiModelProperty(value ="null" )
	
	private String loginBannerVue;
	
	@ApiModelProperty(value ="null" )
	
	private String materialDesc;
	
	@ApiModelProperty(value ="用于小程序二维码海报合成" )
	
	private String miniBackPic;
	
	@ApiModelProperty(value ="null" )
	
	private String miniPic;
	
	@ApiModelProperty(value ="逗号隔开" )
	
	private String orderMobiles;
	
	@ApiModelProperty(value ="null" )
	
	private String orgLiscenseNo;
	
	@ApiModelProperty(value ="null" )
	
	private String orgLiscensePic;
	
	@ApiModelProperty(value ="维护本公司数据的管理单位" )
	
	private String ownerComId;
	
	@ApiModelProperty(value ="null" )
	
	private String payPwd;
	
	@ApiModelProperty(value ="null" )
	
	private String pkId;
	
	@ApiModelProperty(value ="null" )
	
	private String policePic;
	
	@ApiModelProperty(value ="数字低的在前面" )
	
	private Long priorityLevel;
	
	@ApiModelProperty(value ="null" )
	
	private String proEditPage;
	
	@ApiModelProperty(value ="null" )
	
	private Long registCapital;
	
	@ApiModelProperty(value ="null" )
	
	private String registContract;
	
	@ApiModelProperty(value ="注册时填写的申请账号" )
	
	private String registLoginId;
	
	@ApiModelProperty(value ="null" )
	
	private String registMd5Pwd;
	
	@ApiModelProperty(value ="验证过的" )
	
	private String registMobileNo;
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value ="null" )
	
	private String rowId;
	
	@ApiModelProperty(value ="null" )
	
	private String saleSmsAccount;
	
	@ApiModelProperty(value ="null" )
	
	private Double satifySocre;
	
	@ApiModelProperty(value ="null" )
	
	private String servicePromiss;
	
	@ApiModelProperty(value ="O2O中不需要特指的商品" )
	
	private String serviceProCode;
	
	@ApiModelProperty(value ="null" )
	
	private Long settleDay;
	
	@ApiModelProperty(value ="例如北京丰台体验馆" )
	
	private String shopLocation;
	
	@ApiModelProperty(value ="例如9：00-21：00" )
	
	private String shopOpenTime;
	
	@ApiModelProperty(value ="对于开通商城的选择商城模板" )
	
	private String shopTemplate;
	
	@ApiModelProperty(value ="null" )
	
	private String smsAccount;
	
	@ApiModelProperty(value ="null" )
	
	private Long sortNo;
	
	@ApiModelProperty(value ="null" )
	
	private String storeType;
	
	@ApiModelProperty(value ="null" )
	
	private String supApCode;
	
	@ApiModelProperty(value ="null" )
	
	private String supLvl;
	
	@ApiModelProperty(value ="null" )
	
	private String sysName;
	
	@ApiModelProperty(value ="null" )
	
	private String taxCertPic;
	
	@ApiModelProperty(value ="null" )
	
	private String taxNo;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateDt;
	
	@ApiModelProperty(value ="null" )
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUid;
	
	@ApiModelProperty(value ="null" )
	
	private String urlAddr;
	
	@ApiModelProperty(value ="null" )
	
	private String watermarkImgPath;

}