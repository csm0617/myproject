package com.csm.myproject.result.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author 快乐小柴
 * @Date 2022/10/27 13:03
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

//    @ApiModelProperty("用户id")
//    private Long id;

    @ApiModelProperty("用户名")
    private String username;

//    @ApiModelProperty("密码")
//    private String pwd;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("生日")
    private LocalDate birth;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像")
    private String avatar;

//    @ApiModelProperty("登录token")
//    private String apiToken;
}
