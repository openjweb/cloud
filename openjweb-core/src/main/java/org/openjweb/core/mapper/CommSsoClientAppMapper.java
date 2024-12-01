package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommSsoClientApp;
import java.util.List;

@Mapper
public interface CommSsoClientAppMapper  extends  BaseMapper<CommSsoClientApp> {

    @Select("SELECT * FROM comm_sso_client_app WHERE account = #{account}")
    CommSsoClientApp selectByAccountId(@Param("account") String account)  ;

}
