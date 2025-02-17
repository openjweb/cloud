package org.openjweb.core.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommAuth;
import org.openjweb.core.entity.CommColumnDef;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.mapper.CommColumnDefMapper;
import org.openjweb.core.mapper.CommUserMapper;
import org.openjweb.core.module.params.CommAuthParam;
import org.openjweb.core.module.params.CommUserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
//public class CommUserService   implements UserDetailsService {
public class CommUserService  extends ServiceImpl<CommUserMapper, CommUser >  implements UserDetailsService {

    @Autowired
    private CommUserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CommUser user = this.userMapper.selectUserByLoginId(username);
        //返回的时候如果不需要那么多信息可以重构一个对象
        CommUser userDetail = null;
        userDetail = new CommUser(user.getUserId(),user.getLoginId(),user.getPassword(),getUserAuthority(user.getLoginId()));

        return userDetail;
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param loginId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(String loginId) {
        // 实际怎么写以数据表结构为准，这里只是写个例子
        // 角色(比如ROLE_admin)，菜单操作权限(比如sys:user:list)

        List<String> authList = this.userMapper.selectAuthorities(loginId);

        String auths = "";
        if(authList!=null&&authList.size()>0){
            auths = String.join(",",authList);
        }
        //log.info("权限列表:"+auths);
        if(auths!=null&&auths.length()>30) {
            log.info("已读取到权限列表:"+auths.substring(0,30)+"......");
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(auths);
    }

    public List<String> getAuth(String loginId){
        return this.userMapper.selectAuthorities(loginId);
    }


    public CommUser  selectUserByLoginId(String loginId){
        return this.userMapper.selectUserByLoginId(loginId);
    }

    /**
     * wxOpenId对应comm_user的wx_open_id字段
     * @param wxOpenId
     * @return
     */

    public List<CommUser> selectUserByWxOpenId(String wxOpenId){
        return this.userMapper.selectUserByWxOpenId(wxOpenId);
    }

    public void addWeixinUser(String comId,String openId){

        log.info("插入微信用户..................");
        CommUserParam user = new CommUserParam();
        user.setWxOpenId(openId);
        user.setComId(comId);

        user.setRowId(StringUtil.getUUID());
        user.setUserEmail(user.getRowId()+"@openjweb.com");//因为非空，默认填写一个
        user.setCreateDt(StringUtil.getCurrentDateTime());
        user.setCreateUid("system");
        user.setUpdateDt(user.getCreateDt());
        user.setUpdateUid("system");
        user.setLoginId(openId);
        user.setUsername(openId);
        user.setUserMobile(openId);//不能重复
        //先设置一个测试用户注册
        user.setDeptId("29205");//所属部门 ，这个去对应公司的默认部门
        //String deptId =
        user.setIsInUse("Y");
        user.setPassword(StringUtil.getUUID());//微信登录不需要密码，随便设置个随机的

        this.userMapper.insert(user);
        log.info("插入微信用户完毕..................");

        //设置部门



    }



}
