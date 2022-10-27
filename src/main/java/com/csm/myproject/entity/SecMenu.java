package com.csm.myproject.entity;

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
@TableName("m_sec_menu")
@ApiModel(value = "SecMenu对象", description = "")
public class SecMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单类型")
    private Integer menuType;

    @ApiModelProperty("创建时间")
    private Long creatTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("信息")
    private String info;

    @ApiModelProperty("逻辑删除")
    private Integer logicDelete;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

    @Override
    public String toString() {
        return "SecMenu{" +
            "id=" + id +
            ", menuType=" + menuType +
            ", creatTime=" + creatTime +
            ", updateTime=" + updateTime +
            ", name=" + name +
            ", info=" + info +
            ", logicDelete=" + logicDelete +
        "}";
    }
}
