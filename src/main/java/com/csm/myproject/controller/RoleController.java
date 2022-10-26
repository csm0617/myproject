package com.csm.myproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.Role;
import com.csm.myproject.entity.UserRole;
import com.csm.myproject.mapper.RoleMapper;
import com.csm.myproject.response.Response;
import com.csm.myproject.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    @Operation(summary = "分页展示所有的角色")
    @GetMapping("/{pageNum}/{pageSize}")
    public Response<Page<Role>> getRoles(@Parameter(description = "页码默认为1") @PathVariable Integer pageNum,
                                         @Parameter(description="每页显示数量默认的10")@PathVariable Integer pageSize) {

        Page<Role> page = new Page(pageNum, pageSize);
        roleMapper.selectPage(page,null);
        return Response.ok(page);
    }


    @Operation(summary = "通过角色id来查询关联的用户信息")
    @GetMapping("/{roleId}")
    public Response<List<UserRole>> getUsersByRoleId(@Parameter(description = "角色id")@PathVariable Long roleId){
        List<UserRole> usersByRoleId= roleService.getUsers(roleId);
        return Response.ok(usersByRoleId);
    }


    @Operation(summary = "根据条件查询角色")
    @GetMapping()
    public Response<Page<Role>> getRolosBySearch(@Parameter(description = "页码默认为1")@RequestParam Integer pageNum,
                                                 @Parameter(description="每页显示数量默认的10")@RequestParam Integer pageSize,
                                                 @Parameter(description = "角色名")@RequestParam String name,
                                                 @Parameter(description = "角色信息")@RequestParam String info){
        Page<Role> page = new Page(pageNum, pageSize);
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.like(StringUtils.isNotBlank(name),Role ::getName,name).like(StringUtils.isNotEmpty(info), Role ::getInfo,info);
        roleMapper.selectPage(page,roleLambdaQueryWrapper);
        return Response.ok(page);
    }


    @Operation(summary = "新增或修角色")
    @PostMapping()
    public Response<Boolean> insertAndSave(@Parameter(description = "请传入一个需要增加的角色")@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Response.success("新增或修改角色成功",true);
    }
    @Operation(summary = "通过角色id删除角色")
    @DeleteMapping("/{roleId}")
    public Response<Boolean>  deleteRoleById(@Parameter(description = "角色id")@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return Response.success("成功删除角色",true);
    }

}
