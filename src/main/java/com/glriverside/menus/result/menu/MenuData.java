package com.glriverside.menus.result.menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 快乐小柴
 * @Date 2022/10/27 13:36
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuData {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("菜单类型")
    private Integer menuType;

    @ApiModelProperty("创建时间")
    private Long creatTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("信息")
    private String info;
}
