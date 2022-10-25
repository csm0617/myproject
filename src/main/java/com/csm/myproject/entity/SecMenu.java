package com.csm.myproject.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("m_sec_menu")
public class SecMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer menuType;

    private Long creatTime;

    private Long updateTime;

    private String name;
    private String info;

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
    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }
    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }
    public Long getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "SecMenu{" +
                "id=" + id +
                ", menuType=" + menuType +
                ", creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", logicDelete=" + logicDelete +
                '}';
    }
}
