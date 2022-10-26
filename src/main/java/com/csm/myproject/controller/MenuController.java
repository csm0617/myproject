package com.csm.myproject.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.Menu;
import com.csm.myproject.exception.AppException;
import com.csm.myproject.exception.AppExceptionCodeMsg;
import com.csm.myproject.mapper.FirMenuMapper;
import com.csm.myproject.mapper.MenuMapper;
import com.csm.myproject.response.Response;
import com.csm.myproject.service.IFirMenuService;
import com.csm.myproject.service.IMenuService;
import com.csm.myproject.vo.MenuItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@RestController
@RequestMapping("/myproject/menus")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private FirMenuMapper firMenuMapper;


    @GetMapping("/list")
    @ApiOperation("展开菜单列表")
    public Response<Menu> showMenuList(@Parameter(description = "页码默认为1") @RequestParam(defaultValue = "1") Integer pageNum,
                                       @Parameter(description = "每页显示数量默认的10") @RequestParam(defaultValue = "10") Integer pageSize,
                                       @Parameter(description = "菜单id") @RequestParam(required = false) Long menuId,
                                       @Parameter(description = "菜单的类型") @RequestParam Integer type
    ) {
        //数据校验
        if (!(type >= 1 && type <= 3)) {
            throw new AppException(AppExceptionCodeMsg.INPUT_INVALID);
        }
        if (type == 1) {
            Page<Menu> page = menuService.showMenuList(pageNum, pageSize);
            return Response.ok(page);
        }
        if (menuId != null && type == 2) {
            Page<Menu> page = menuService.showFirMenuList(pageNum, pageSize, menuId);
            return Response.ok(page);

        }
        if (menuId != null && type == 3) {
            Page<Menu> page = menuService.showSecMenuList(pageNum, pageSize, menuId);
            return Response.ok(page);
        }
        return Response.error(AppExceptionCodeMsg.NOT_FIND_MENU);
    }

    @Operation(summary = "通过角色id查找该角色的全部菜单")
    @GetMapping("/{roleId}")
    public Response<List<MenuItem>> showRoleMenuList(@Parameter(description = "角色id")@PathVariable Long roleId) {
        //数据格式校验
        if (!(roleId instanceof Long)){
            throw new AppException(1000,"输入的角色id类型有误");
        }
        List<MenuItem> menuItems = menuService.showMenuListByRoleId(roleId);
        return Response.ok(menuItems);
    }

    @Transactional
    @Operation(summary = "通过菜单id和菜单类型来删除菜单")
    @DeleteMapping("")
    public Response<Boolean> deleteMenu(@Parameter(description = "需要删除菜单的id")@RequestParam Long menuId,
                                        @Parameter(description = "需要删除菜单的类型")@RequestParam Integer type)
            throws Exception {
        try {
            if (menuService.deleteMenuById(menuId, type)){
                return Response.success("删除菜单成功",true);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return Response.error(AppExceptionCodeMsg.DELETE_ERR_MSG);
    }

    @Operation(summary = "增加菜单")
    @PostMapping("")
    public Response<Boolean> insertMenu(@Parameter(description = "请传入一个需要增加的菜单")@RequestBody Menu menu) {
        if (menuService.insertMenu(menu) != null) {
            return Response.success("插入菜单成功",true);
        }
        return Response.error(AppExceptionCodeMsg.INSERT_ERR_MSG);
    }

    @Operation(summary = "修改菜单")
    @PutMapping("")
    public Response<Boolean> updateMenu(@Parameter(description = "请传入一个需要修改的菜单")@RequestBody Menu menu) {
        if (menuService.updateMenu(menu) != null) {
            return Response.success("修改菜单成功",true);
        }
        return Response.error(AppExceptionCodeMsg.UPDATE_ERR_MSG);
    }

}
