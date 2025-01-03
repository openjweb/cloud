package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommUser;

import java.util.List;

@Mapper
public interface CommUserMapper extends BaseMapper<CommUser> {

    //@Select("SELECT * FROM comm_user WHERE login_id = #{loginId}")
    @Select("SELECT user_id,login_id,row_id,username,password,com_id,dept_id,pwd_type," +
            "emp_no,user_email,user_mobile,real_name,regist_mobile,is_in_use ," +
            "psn_photo_path,is_mobile_valid,wx_open_id,sort_no, create_dt,update_dt," +
            "create_uid,update_uid FROM comm_user WHERE login_id = #{loginId}")
    CommUser selectUserByLoginId(@Param("loginId") String loginId);

    @Select("SELECT comm_code FROM v_user_auth WHERE login_id = #{loginId} order by comm_code ")
    //@Select("SELECT comm_code FROM demo_user_auth WHERE login_id = #{loginId} order by comm_code ")
    //@Select("SELECT comm_code FROM v_user_auth_demo WHERE login_id = #{loginId} order by comm_code ")

    List<String> selectAuthorities(@Param("loginId") String loginId);

    @Select("select * from comm_user where wx_open_id=#{wxOpenId}")
    List<CommUser> selectUserByWxOpenId(@Param("wxOpenId") String wxOpenId);

}
