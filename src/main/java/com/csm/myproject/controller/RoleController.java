package com.csm.myproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.Role;
import com.csm.myproject.entity.UserRole;
import com.csm.myproject.mapper.RoleMapper;
import com.csm.myproject.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@RestController
@RequestMapping("/myproject/role")
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IRoleService roleService;

    @GetMapping("/{pageNum}/{pageSize}")
    public Page getRoles(@PathVariable Integer pageNum,
                         @PathVariable Integer pageSize) {

        Page page = new Page(pageNum, pageSize);
        roleMapper.selectPage(page,null);
        return page;
    }
    @GetMapping("/{roleId}")
    public List<UserRole> getUsersByRoleId(@PathVariable Long roleId){
        List<UserRole> usersByRoleId= roleService.getUsers(roleId);
        return usersByRoleId;
    }
    @GetMapping()
    public Page getRolosBySearch(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String name,
                                 @RequestParam String info){
        Page page = new Page(pageNum, pageSize);
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.like(StringUtils.isNotBlank(name),Role ::getName,name).like(StringUtils.isNotEmpty(info), Role ::getInfo,info);
        roleMapper.selectPage(page,roleLambdaQueryWrapper);
        return page;
    }
    @PostMapping()
    public Role insertAndSave(@RequestBody Role userRole) {
        roleService.saveOrUpdate(userRole);
        return userRole;
    }

    @DeleteMapping("/{roleId}")
    public void  deleteRoleById(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
    }


}
