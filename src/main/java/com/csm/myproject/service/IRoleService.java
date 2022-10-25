package com.csm.myproject.service;

import com.csm.myproject.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csm.myproject.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Repository
public interface IRoleService extends IService<Role> {
    List<UserRole> getUsers(Long roleId);

    void deleteRole(Long roleId);

}
