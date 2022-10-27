package com.csm.myproject.result.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 快乐小柴
 * @Date 2022/10/27 13:34
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleData {
    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色信息")
    private String info;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色类型")
    private Integer roleType;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("创建时间")
    private Long creatTime;
}
