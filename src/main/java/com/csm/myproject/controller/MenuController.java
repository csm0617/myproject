package com.csm.myproject.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.Menu;
import com.csm.myproject.mapper.FirMenuMapper;
import com.csm.myproject.mapper.MenuMapper;
import com.csm.myproject.service.IMenuService;
import com.csm.myproject.vo.MenuItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Page showMenuList(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam Long menuId,
                             @RequestParam Integer type
                            ){
        if (type==1){
            Page page = menuService.showMenuList(pageNum, pageSize);
            return page;
        }
        if (menuId!=null&&type==2){
            Page page = menuService.showFirMenuList(pageNum, pageSize, menuId);
            return page;

        }
        if (menuId!=null&&type==3){
            Page page = menuService.showSecMenuList(pageNum, pageSize, menuId);
            return page;
        }
        return null;
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
