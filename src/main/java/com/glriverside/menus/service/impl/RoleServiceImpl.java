package com.glriverside.menus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.glriverside.menus.entity.Role;
import com.glriverside.menus.entity.RoleMenu;
import com.glriverside.menus.entity.UserRole;
import com.glriverside.menus.mapper.RoleMapper;
import com.glriverside.menus.mapper.RoleMenuMapper;
import com.glriverside.menus.mapper.UserRoleMapper;
import com.glriverside.menus.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<UserRole> getUsers(Long roleId) {

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleId,roleId);
        List<UserRole> userByRoleId = userRoleMapper.selectList(queryWrapper);
        return userByRoleId;
    }

    @Override
    public void deleteRole(Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleId,roleId);
        userRoleMapper.delete(queryWrapper);
        LambdaQueryWrapper<RoleMenu> roleMenuQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuQueryWrapper.eq(RoleMenu::getRoleId,roleId);
        roleMenuMapper.delete(roleMenuQueryWrapper);
        roleMapper.deleteById(roleId);
    }

    @Override
    public boolean findRoleByName(String name) {
        Role role = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(name != null, Role::getName, name));
        if (role !=null) {
            return true;
        }
        return false;
    }
}
