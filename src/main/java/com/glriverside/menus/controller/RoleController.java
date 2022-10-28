package com.glriverside.menus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glriverside.menus.entity.Role;
import com.glriverside.menus.entity.UserRole;
import com.glriverside.menus.exception.AppException;
import com.glriverside.menus.exception.AppExceptionCodeMsg;
import com.glriverside.menus.mapper.RoleMapper;
import com.glriverside.menus.response.Response;
import com.glriverside.menus.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "角色")
@RestController
@RequestMapping("/v1/role")
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IRoleService roleService;


    @ApiOperation(value = "分页展示所有的角色")
    @GetMapping("/{pageNum}/{pageSize}")
    public Response<Page<Role>> getRoles(@ApiParam(value = "页码默认为1") @PathVariable Integer pageNum,
                                         @ApiParam(value = "每页显示数量默认的10") @PathVariable Integer pageSize) {

        Page<Role> page = new Page(pageNum, pageSize);
        roleMapper.selectPage(page, null);
        return Response.ok(page);
    }


    @ApiOperation(value = "通过角色id来查询关联的用户信息")
    @GetMapping("/{roleId}")
    public Response<List<UserRole>> getUsersByRoleId(@ApiParam(value = "角色id") @PathVariable Long roleId) {
        List<UserRole> usersByRoleId = roleService.getUsers(roleId);
        return Response.ok(usersByRoleId);
    }


    @ApiOperation(value = "根据条件查询角色")
    @GetMapping()
    public Response<Page<Role>> getRolosBySearch(@ApiParam(value = "页码默认为1") @RequestParam Integer pageNum,
                                                 @ApiParam(value = "每页显示数量默认的10") @RequestParam Integer pageSize,
                                                 @ApiParam(value = "角色名") @RequestParam String name,
                                                 @ApiParam(value = "角色信息") @RequestParam String info) {
        Page<Role> page = new Page(pageNum, pageSize);
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.like(StringUtils.isNotBlank(name), Role::getName, name).like(StringUtils.isNotEmpty(info), Role::getInfo, info);
        roleMapper.selectPage(page, roleLambdaQueryWrapper);
        return Response.ok(page);
    }


    @ApiOperation(value = "新增或修角色")
    @PostMapping()
    public Response<Boolean> insertRole(@ApiParam(value = "请传入一个需要增加的角色") @RequestBody Role role) {
        if (!roleService.findRoleByName(role.getName())) {
            if (roleMapper.insert(role) > 0) {
                return Response.success("新增色成功", true);
            } else {
                return Response.error(AppExceptionCodeMsg.INSERT_ERR_MSG);
            }

        } else {
            throw new AppException(AppExceptionCodeMsg.ROLE_ALREADY_EXISTS_MSG);
        }
    }

    @ApiOperation(value = "修改角色")
    @PutMapping
    public Response<Boolean> updateRole(@ApiParam(value = "请传入一个需要修改的角色") @RequestBody Role role) {
            if (roleMapper.update(role, new LambdaQueryWrapper<Role>().eq(Role::getId, role.getId())) > 0) {
                return Response.success("修改角色成功", true);
            } else {
                return Response.error(AppExceptionCodeMsg.UPDATE_ERR_MSG);
            }
    }

    @ApiOperation(value = "通过角色id删除角色")
    @DeleteMapping("/{roleId}")
    public Response<Boolean> deleteRoleById(@ApiParam(value = "角色id") @PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return Response.success("成功删除角色", true);
    }
}
