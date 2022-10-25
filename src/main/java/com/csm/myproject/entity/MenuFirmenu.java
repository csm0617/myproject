package com.csm.myproject.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@TableName("m_menu_firmenu")
public class MenuFirmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long menuId;

    private Long firmenuId;

    @TableLogic
    private Integer logicDelete;

    public Integer getLogicDelete() {
        return logicDelete;
    }


    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

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

    @Override
    public String toString() {
        return "MenuFirmenu{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", firmenuId=" + firmenuId +
                ", logicDelete=" + logicDelete +
                '}';
    }
}
