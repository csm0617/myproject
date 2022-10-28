package com.glriverside.menus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author csm
 * @since 2022-10-27
 */
@TableName("m_menu_firmenu")
@ApiModel(value = "MenuFirmenu对象", description = "")
public class MenuFirmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("一级菜单id")
    private Long menuId;

    @ApiModelProperty("二级菜单id")
    private Long firmenuId;

    @ApiModelProperty("逻辑删除")
    private Integer logicDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public Long getFirmenuId() {
        return firmenuId;
    }

    public void setFirmenuId(Long firmenuId) {
        this.firmenuId = firmenuId;
    }
    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

    @Override
    public String toString() {
        return "MenuFirmenu{" +
            "id=" + id +
            ", menuId=" + menuId +
            ", firmenuId=" + firmenuId +
            ", logicDelete=" + logicDelete +
        "}";
    }
}
