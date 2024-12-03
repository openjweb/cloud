package org.openjweb.core.entity;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Slf4j
@Data
@TableName("comm_user")
public class CommUser implements UserDetails, Serializable {

    /******* 非数据库属性开始 ******/

    private final Collection<? extends GrantedAuthority> authorities  ;
    private final boolean accountNonExpired;//账号是否过期
    private final boolean accountNonLocked;//账号是否锁定
    private final boolean credentialsNonExpired;//凭证是否过期
    private final boolean enabled;//是否启用

    /******* 非数据库属性结束 ******/
    @TableId(type = IdType.ASSIGN_UUID)
    private Long userId; //数字唯一主键
    private String rowId;//32位唯一ID(因为项目中有所以保留32位UUID)

    /* 登录必须属性开始 */
    private String loginId;//业务系统登录账号，以后等同username
    private final String username;//以后等同于loginId
    private String password;//加密后的密码

    /* 业务必填字段 */
    private String comId;  //所属公司ID
    private String deptId; //所属部门
    private String pwdType;//密码类型 MD5，AES,SM,BCR 默认为MD5
    private String empNo; //员工工号
    private String userEmail;//邮箱（如没有可随机一个）
    private String userMobile;//手机（如没有可随机一个）
    private String realName; //真实姓名
    private String registMobile;//注册时填写的手机名称

    /*重要字段*/
    private String isInUse; //账号是否启用，未启动则不能登录
    private String psnPhotoPath = "";//头像图片路径+文件名
    private String isMobileValid;//是否手机认证通过
    private String wxOpenId;//绑定的微信OpenID，以后放扩展表

    /*时间戳 修改人 乐观锁等 */
    private Long sortNo;//排序号
    @TableField(fill = FieldFill.INSERT)
    private String createDt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateDt;
    @TableField(fill = FieldFill.INSERT)

    private String createUid;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUid;



    /*
    private String comName;//所属机构名称
    private String positionName;//职位名称
    private String registComName;//注册时填写的工作单位
    private String registPosition;//注册时填写的职位名称
    private String userTel;
    private String userTitle;
    private String userType;
    private String confirmPassword;
    //-------网站会员相关---------------
    private String msnEmail;
    private String qqNo;
    private String isPortalMember;
    private String idCard;//居民身份证
    private String workPlace;//工作城市
    private String birthDay;//生日
    private String userSex;//性别
    private String provinceId;//省份
    private String cityId;//城市
    private String countyId;//县乡
    private String registIp;//用户注册的IP地址
    private String isMarry;//是否已婚
    private String randNumber = "";//注册时使用的验证码，不需要对应到数据库
    private String industId = "";//所属行业--大类
    public String deptName = "";//部门名称
    private String lastLoginDt = "";//最后登录时间
    private String md5Token = "";//md5激活码
    private String workType = "";//工种
    private String userDegree = "";//文化程度
    private String zipCode = "";//邮政编码
    private String contactAddr = "";//联系地址
    private String workStatus = "";//工作状态，在职，离职，退休。。
    private String joinDt = "";//到公司时间
    private String deptCode = "";//部门编码
    private String userLevel = "";// 用户级别
    private String appId = "";//用于标识用户从哪个注册页注册的，以便登陆后跳转到不同的后台首页
    private Double cashBalanceAmt;//现金余额。如充值，资金申请,目前不使用
    private String userClass;//用户分类
    private String pkId;  //补充字段以统一excel 导入算法

    private String liveAddr;//居住地址，对于企业会员为企业地址
    private String activeCode;//激活码
    private String payPwd;//支付密码，md5加密
    private String telExt;//电话分机
    private String allowSendSms;//是否允许发手机短信，暂时用于HR的短信控制，其他系统暂时不控制
    private String testMobile;
    private String testEmail;
    private String isEmailValid;//是否已验证邮箱
    private String subComId;//子公司标记
    private String resetPwd;//用于忘记密码重置口令
    private String isValidIdcard;//是否身份证校验通过

    private String recommendLoginId;//推荐人登录帐号
    private String relationType;// 与推荐人关系
    private String firstChannelCode;//一级推广渠道编码
    private String secondChannelCode;//二级推广渠道编码--这个考虑不用了
    private String nickName;//昵称
    private String pinyinCode;//拼音码，根据姓名生成
    private String domainName;//用户个人域名字段
    private String dynamicUrl;//动态URL
    private String staticUrl;//静态地址
    private String qrcodeUrl;//QRCODE二维码地址
    private String realCom;//分管公司代码，实际此员工为其他单位，为录入数据方便模拟的本单位员工
    private String signMsg;//个性签名
    private String originPwd;//原始口令用于检查MD5加密前是否符合正则表达式
    private String userIntro;//自我介绍
    private String nationId;//所属国家
    private String mobileType;//手机类型 ANDRIOD IOS WPHONE
    private String deviceToken;//手机DEVICE_TOKEN
    private String isComMember;//是否企业会员---如果填写完企业会员信息提交后，设置为企业会员
    private String weixinNo;//微信号
    private String histPwd;//历史口令
    private String pwdUpdateDt;//密码最后修改时间
    private Long loginFailCount;//登录失败次数
    private String lastFailLoginDt;//最后一次登录失败的时间
    private String alipayNo;//支付宝账号
    private String alipayName;//支付宝名
    private String blockChainAccount;//区块链私链账户，用于区块链演示和测试
    private String orgRowId;//所属机构--对应comm_psn_company

    private String initPwd;//
    private String msgOpenId;

    private String zhouId;//所属大洲
    private String idImage1; //身份证正面
    private String idImage2; //身份证反面
    //以后不用,或者转到用户扩展信息表
    private String isHrInUse = "";// HR账号是否启用
    private String ccGroupCode;//主要用于呼叫中心座席的分组
    private String priKey;//针对 blockChainAccount 的 区块链私钥，测试使用
    private String rcmdStoreId;//推荐店铺--以后放别的表
    private Double comRegistCapital;//企业会员-本企业注册资本
    private Long comPsnCount;//企业人数 ---针对企业会员，填写企业人数
    private Double comYearSaleAmt;//企业年销售额--针对企业会员，填写企业年销售额
    private Double cashBalance;//获取资金余额
    private String agreeFlag = "";//是否同意遵守本网站协议的标志，暂未对应到数据库字段
    private String newMailFlag = "";//是否要求创建本网站邮箱的标志，暂未对应到数据库字段
    private String pwdQuest1;//密码提示问题1---应该设置子表
    private String pwdAnswer1;//密码回答问题1
    private String pwdQuest2;//密码提示问题2
    private String pwdAnswer2;//密码回答问题2
    private String pwdQuest3;//密码提示问题3
    private String pwdAnswer3;//密码回答问题3
    private String vipStartDt;
    private String vipEndDt;
    private String memName;//公司级会员等级名，不是会员名字
    private String memStartDt;//会员有效开始日
    private String memEndDt;//会员有效结束日
    private String userCountyText;//用户省市县文字
    private String currFrameCode;//选择的框架编号
    private String currSkinCode;//选择的皮肤编号
    private String themeColor;//主题颜色
    private String isRandSkin;//是否启用随机皮肤
    */

    public CommUser(){
        this.userId = null;
        this.username = null;
        this.loginId = null;
        this.password = null;
        this.enabled = false;
        this.accountNonExpired = false;
        this.credentialsNonExpired = false;
        this.accountNonLocked = false;
        this.authorities = null;
    }


    public CommUser(Long userId, String loginId, String password, Collection<? extends GrantedAuthority> authorities) {
        this(userId, loginId, password, true, true, true, true, authorities);
    }

    public CommUser(Long userId, String loginId, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        Assert.isTrue(loginId != null && !"".equals(loginId) && password != null, "Cannot pass null or empty values to constructor");
        this.userId = userId;
        this.username = loginId;
        this.loginId = loginId;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    public CommUser(Long userId,String loginId,String rowId,String username,String password,
                    String comId,String deptId,String pwdType,String empNo,String userEmail,
                    String userMobile,String realName,String registMobile,String isInUse,String psnPhotoPath,
                    String isMobileValid,String wxOpenId,Long sortNo,String createDt,
                    String updateDt,String createUid,String updateUid){
        this.userId = userId;
        this.username = username;
        this.rowId = rowId;
        this.loginId = loginId;
        this.password = password;
        this.comId = comId;
        this.deptId = deptId;
        this.pwdType = pwdType;
        this.empNo = empNo;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.realName = realName;
        this.registMobile = registMobile;
        this.isInUse = isInUse;
        this.enabled = "Y".equals(isInUse)?true:false;//
        this.psnPhotoPath = psnPhotoPath;
        this.isMobileValid = isMobileValid;
        this.wxOpenId = wxOpenId;
        this.sortNo = sortNo;
        this.createDt = createDt;
        this.updateDt = updateDt;
        this.createUid = createUid;
        this.updateUid = updateUid;//
        //账号未过期需要增加一个判断，可以通过lastLoginDt与
        this.accountNonExpired = this.enabled;//accountNonExpired;
        this.credentialsNonExpired = false;//最好动态判断
        this.accountNonLocked = enabled;
        this.authorities = getAuthorities() ;//这里先设置下，否则提示authorities需要初始化

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
