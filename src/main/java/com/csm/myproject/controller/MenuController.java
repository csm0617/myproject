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
import io.swagger.v3.oas.annotations.Parameter;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Api("菜单管理")
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
                             @Parameter(description = "菜单id")@RequestParam(required = false) Long menuId,
                             @Parameter(description = "菜单的类型")@RequestParam Integer type
                            ){
        //数据校验
        if (!(type>=1&&type<=3)){
            throw new AppException(AppExceptionCodeMsg.INPUT_INVALID);
        }

        if (type==1){
            Page<Menu> page = menuService.showMenuList(pageNum, pageSize);
            return Response.ok(page);
        }
        if (menuId!=null&&type==2){
            Page<Menu> page = menuService.showFirMenuList(pageNum, pageSize, menuId);
            return Response.ok(page);

        }
        if (menuId!=null&&type==3){
            Page<Menu> page = menuService.showSecMenuList(pageNum, pageSize, menuId);
            return Response.ok(page);
        }
        return Response.error(AppExceptionCodeMsg.NOT_FIND_MENU);
    }


    @GetMapping("/{roleId}")
    public List<MenuItem> showRoleMenuList(@PathVariable Long roleId){

        return menuService.showMenuListByRoleId(roleId);
    }

    @DeleteMapping("/{menuId}/{type}")
    public boolean deleteMenu(@PathVariable Long menuId,@PathVariable Integer type) throws Exception{
        try {
            return menuService.deleteMenuById(menuId, type);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @PostMapping("")
    public Boolean insertMenu(@RequestBody Menu menu) {
        if (menuService.insertMenu(menu) != null) {
            return true;
        }
        return false;
    }
    @PutMapping("")
    public Boolean updateMenu(@RequestBody Menu menu) {
         if (menuService.updateMenu(menu)!=null){
             return true;
         }
         return false;
    }

}
