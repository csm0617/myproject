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
@TableName("m_firmenu_secmenu")
public class FirmenuSecmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long firmenuId;

    private Long secmenuId;
    @TableLogic
    private Integer logicDelete;

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

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

    @Override
    public String toString() {
        return "FirmenuSecmenu{" +
                "id=" + id +
                ", firmenuId=" + firmenuId +
                ", secmenuId=" + secmenuId +
                ", logicDelete=" + logicDelete +
                '}';
    }
}
