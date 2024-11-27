package org.openjweb.core.service;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.mapper.CommUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class CommUserService implements UserDetailsService {
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

}
