package com.csm.myproject.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@TableName("m_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色标识符
     */
    private String info;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色类型
     */
    private Integer roleType;

    private Long updateTime;

    private Long creatTime;
    @TableLogic

    private Integer logicDelete;

    public void setInfo(String info) {
        this.info = info;
    }

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
    public String getInfo() {
        return info;
    }

    public void info(String info) {
        this.info = info;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", name='" + name + '\'' +
                ", roleType=" + roleType +
                ", updateTime=" + updateTime +
                ", creatTime=" + creatTime +
                ", logicDelete=" + logicDelete +
                '}';
    }
}
