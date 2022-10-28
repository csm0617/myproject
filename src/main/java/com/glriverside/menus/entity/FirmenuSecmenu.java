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
@TableName("m_firmenu_secmenu")
@ApiModel(value = "FirmenuSecmenu对象", description = "")
public class FirmenuSecmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("二级菜单id")
    private Long firmenuId;

    @ApiModelProperty("三级菜单id")
    private Long secmenuId;

    @ApiModelProperty("逻辑删除")
    private Integer logicDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Long getFirmenuId() {
        return firmenuId;
    }

    public void setFirmenuId(Long firmenuId) {
        this.firmenuId = firmenuId;
    }
    public Long getSecmenuId() {
        return secmenuId;
    }

    public void setSecmenuId(Long secmenuId) {
        this.secmenuId = secmenuId;
    }
    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

    @Override
    public String toString() {
        return "FirmenuSecmenu{" +
            "id=" + id +
            ", firmenuId=" + firmenuId +
            ", secmenuId=" + secmenuId +
            ", logicDelete=" + logicDelete +
        "}";
    }
}
