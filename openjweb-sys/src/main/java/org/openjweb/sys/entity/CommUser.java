package org.openjweb.sys.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommUser {
    @ApiModelProperty(value ="登录账号",required = true)
    private String loginId;

    @ApiModelProperty(value ="用户昵称")
    private String username;

    @ApiModelProperty(value ="真实姓名")
    private String realName;

    @ApiModelProperty(value ="用户性别：男M 女F")
    private String userSex;

}
