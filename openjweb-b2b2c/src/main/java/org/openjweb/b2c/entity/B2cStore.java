package org.openjweb.b2c.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("b2c_store")
public class B2cStore implements Serializable {
    //商家实体类
    //第一步建实体类 第二步 建参数类
    @TableId(type = IdType.ASSIGN_UUID)
    private String rowId;
    private Long sortNo;

    @TableField(fill = FieldFill.INSERT)
    private String createDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateDt;

    @TableField(fill = FieldFill.INSERT)
    private String createUid;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUid;

    private String dataFlg;
    private String objName;
    private String masterRowId;
    private String flowTransId;

    @Version
    private Long recordVersion=0L;
    private String flowStatus;
    private String stCode;
    private String stClsCode;
    private Long stCid;
    private String stBulletin;
    private String isTaobao;
    private String stCeateDt;
    private Long remainCount;
    private String deliveryScore;
    private String loginId;
    private Double stXpos;
    private Long allCount;
    private String stDesc;
    private Double stYpos;
    private String stAddr;
    private String deptRowId;
    private String serviceScore;
    private String itemScore;
    private String picPath;
    private String sellerNick;
    private String stTitle;
    private String stName;
    private String comId;
    private Long stTaobaoCode;
    //private String settleType;//商户资金结算方式：WX 微信钱包实时，ZFT或空或空串为目前，ZFT2为新版支付通分润暂未开发
    private String stDesc2;
    private String bizTime;
    private String smallImage;
    private String largeImage;
    private String stMarket;
    private String isEntityShop;
    private String stType;
    private String parentStId;
    private String stProvince;
    private String stCity;
    private String stCounty;
    private String applySellerLvl;
    private String realSellerLvl;
    private String weixinAccountId;
    private String openId;
    private String checkStatus;
    private String storeIndust;
    private Double discountRate;
    private String bossMobile;
    private String bankType;
    private String bankName;
    private String bankAcctNo;
    private String bankAcctName;
    private String bizLiscenseNo;
    private String qrcodeImage;
    private Double scoreConvertRate;
    private String tsRowId;
    private String tsName;
    private String tzRowId;
    private String tzName;
    private String tzProxyRowid;
    private String tzProxyName;
    private String adminOpenId;
    private String adminMobileNo;
    private String keyWords;
    private String emailAddr;
    private String clsName;
    private String zftMerchantNo;
    private String idCardNo;
    private String bankReservMobile;
    private String isOpenZc;
    private String o2oSettleType;
    private Double withdrawRatio;
    private String zftSignKey;
    private String zftFeeRate;
    private String liscenseDate;
    private String legalPersonName;
    private String legalPersonTel;
    private String legalEmail;
    private String legalIdCardNo;
    private String validityFrom;
    private String validityTo;
    private String contactName;
    private String contactMobile;
    private String contactEmail;
    private String zftSerialNo;
    private String isRealtime;
    private String superMarketName;
    private String deptStRowId;
    private String serviceMobileNo;
    private String saleMobileNo;
    private String isAttendSale;
    private String notifyMobiles;
    private String newZftBankCard;
    private String newZftFeeRate;
    private String alipayNo;
    private String alipayName;
    private String realSettleType;
    private String subAppId;
    private String subMchId;
    private String isOpenDiancan;
    private String posSup;
    private String posStCode;
    private String weixinLogoPath;
    private String backPath;
    private String weixinBackPic;
    private String leaderMobileNo;
    private String leaderOpenId;
    private Double leaderFyRate;
    private Double saleFyRate;
    private Double clientFyRate;
    private Double sellerFyRate;
    private String isPlatStore;
    private String gzPic;
    private String payPic;
    private String tmplName;
    private String blockchainPlatNo;
    private String blockAppId;
    private String blockAppSecret;
    private String blockStId;
    private String blockPubKey;
    private String blockAddress;
    private String proxyLvl;
    private String stClass;
    private String bannerPic;
    private String contactAddr;
    private String advText;
    private String stManager;
    private String serviceTel;
    private String stZipCode;
    private String stOpenDate;
    private String stRateLevel;
    private String stRateMonth;
    private String stRateWeek;
    private String stRefRegion;
    private String stMemDayType;
    private String stMemDaySet;
    private String stSpecialDay;
    private String isHeader;
    private String recvAddr;
    private String recvMobile;
    private String recvName;
    private String stAttr1;
}
