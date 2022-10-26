package com.csm.myproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.*;
import com.csm.myproject.mapper.*;
import com.csm.myproject.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csm.myproject.vo.MenuItem;
import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithmState;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuFirmenuMapper menuFirmenuMapper;
    @Autowired
    private FirmenuSecmenuMapper firmenuSecmenuMapper;
    @Autowired
    private SecMenuMapper secMenuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private FirMenuMapper firMenuMapper;


//    @Override
//    public List<MenuItem> showMenuList() {
//        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>(null));
//        List<MenuItem> result = new ArrayList<>();
//        for (Menu menu : menus) {
//            Long menuId = menu.getId();
//            MenuItem m1 = new MenuItem();
//            m1.setName(menu.getName());
//            m1.setType(1);
//            result.add(m1);
//            List<MenuItem> m2List = new ArrayList<>();
//
//            List<MenuFirmenu> menuFirmenus = menuFirmenuMapper.selectList(new LambdaQueryWrapper<MenuFirmenu>()
//                    .eq(menuId != null, MenuFirmenu::getMenuId, menuId));
//
//            //循环遍历取出一个一级菜单
//            for (MenuFirmenu menuFirmenu : menuFirmenus) {
//                Long firmenuId = menuFirmenu.getFirmenuId();
//                FirMenu firMenu = firMenuMapper.selectById(firmenuId);
//                MenuItem m2 = new MenuItem();
//                m2.setName(firMenu.getName());
//                m2.setType(2);
//                List<MenuItem> m3List = new ArrayList<>();
//                m2List.add(m2);
//
//                List<FirmenuSecmenu> firmenuSecmenus = firmenuSecmenuMapper.selectList(new LambdaQueryWrapper<FirmenuSecmenu>().
//                        eq(firmenuId != null, FirmenuSecmenu::getFirmenuId, firmenuId));
//                //遍历取出这一个二级菜单对应的三级菜单
//                for (FirmenuSecmenu firmenuSecmenu : firmenuSecmenus) {
//                    Long secmenuId = firmenuSecmenu.getSecmenuId();
//                    SecMenu secMenu = secMenuMapper.selectById(secmenuId);
//                    MenuItem m3 = new MenuItem();
//                    m3.setName(secMenu.getName());
//                    m3.setType(3);
//                    m3List.add(m3);
//                }
//                m2.setSub(m3List);
//            }
//            m1.setSub(m2List);
//        }
//        return result;
//    }

    @Override
    public List<MenuItem> showMenuListByRoleId(Long roleId) {
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenu>()
                .eq(roleId != null, RoleMenu::getRoleId, roleId));
        List<MenuItem> result = new ArrayList<>();

        for (RoleMenu roleMenu : roleMenus) {
            Long menuId = roleMenu.getMenuId();
            //取出一级菜单
            List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                    .eq(menuId != null, Menu::getId, menuId)
            );

            for (Menu menu : menus) {
                MenuItem m1 = new MenuItem();
                m1.setName(menu.getName());
                m1.setType(1);
                result.add(m1);
                List<MenuItem> m2List = new ArrayList<>();

                List<MenuFirmenu> menuFirmenus = menuFirmenuMapper.selectList(new LambdaQueryWrapper<MenuFirmenu>()
                        .eq(menuId != null, MenuFirmenu::getMenuId, menuId));

                //循环遍历取出一个一级菜单
                for (MenuFirmenu menuFirmenu : menuFirmenus) {
                    Long firmenuId = menuFirmenu.getFirmenuId();
                    FirMenu firMenu = firMenuMapper.selectById(firmenuId);
                    MenuItem m2 = new MenuItem();
                    m2.setName(firMenu.getName());
                    m2.setType(2);
                    List<MenuItem> m3List = new ArrayList<>();
                    m2List.add(m2);

                    List<FirmenuSecmenu> firmenuSecmenus = firmenuSecmenuMapper.selectList(new LambdaQueryWrapper<FirmenuSecmenu>().
                            eq(firmenuId != null, FirmenuSecmenu::getFirmenuId, firmenuId));
                    //遍历取出这一个二级菜单对应的三级菜单
                    for (FirmenuSecmenu firmenuSecmenu : firmenuSecmenus) {
                        Long secmenuId = firmenuSecmenu.getSecmenuId();
                        SecMenu secMenu = secMenuMapper.selectById(secmenuId);
                        MenuItem m3 = new MenuItem();
                        m3.setName(secMenu.getName());
                        m3.setType(3);
                        m3List.add(m3);
                    }
                    m2.setSub(m3List);
                }
                m1.setSub(m2List);
            }
        }
        return result;
    }
    @Override
    public boolean deleteMenuById(Long menuId, Integer type) {
        //删除一级菜单
        if (menuId != null && type == 1) {
                //先删除一级菜单和角色表的关联
                roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getMenuId, menuId));
                menuFirmenuMapper.delete(new LambdaQueryWrapper<MenuFirmenu>().eq(MenuFirmenu::getMenuId, menuId));
                if(menuMapper.deleteById(menuId)>0) {
                    return true;
                }
        }

        if (menuId != null && type == 2) {
            Long firMenuId = menuId;
            menuFirmenuMapper.delete(new LambdaQueryWrapper<MenuFirmenu>().eq(MenuFirmenu::getFirmenuId, firMenuId));
            firmenuSecmenuMapper.delete(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getFirmenuId, firMenuId));
            if(firMenuMapper.deleteById(firMenuId)>0){
                return true;
            }

        }
        if (menuId != null && type == 3) {
            Long secMenuId = menuId;
            firmenuSecmenuMapper.delete(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getSecmenuId, secMenuId));
            int secMenuDelete = secMenuMapper.deleteById(secMenuId);
            if (secMenuDelete>0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Menu insertMenu(Menu menu) {
        if(menu.getMenuType()==1){
            menuMapper.insert(menu);
            return menu;
        }
        if (menu.getMenuType() == 2){
            FirMenu firMenu = new FirMenu();
            firMenu.setMenuType(2);
            BeanUtils.copyProperties(menu,firMenu);
            //赋值id可能会失败
            //设置id自增；
            // firMenu.setId(menu.getId());
//            firMenu.setName(menu.getName());
//            firMenu.setInfo(menu.getInfo());
//            firMenu.setCreatTime(menu.getCreatTime());
//            firMenu.setUpdateTime(menu.getUpdataTime());
            firMenuMapper.insert(firMenu);
            return menu;
        }
        if (menu.getMenuType()==3){
            SecMenu secMenu = new SecMenu();
            BeanUtils.copyProperties(menu,secMenu);
            secMenu.setMenuType(3);
//            secMenu.setName(menu.getName());
//            secMenu.setInfo(menu.getInfo());
//            secMenu.setCreatTime(menu.getCreatTime());
//            secMenu.setUpdateTime(menu.getCreatTime());
            secMenuMapper.insert(secMenu);
            return menu;
        }
        return null;
    }

    @Override
    public Menu updateMenu(Menu menu) {
        if (menu.getMenuType()==1){
            Menu menu1 = menuMapper.selectById(menu.getId());
            if (menu1 != null) {
                int update = menuMapper.update(menu, new LambdaQueryWrapper<Menu>()
                        .eq(menu.getId() != null, Menu::getId, menu.getId()));
                if (update > 0) {
                    return menu;
                }
            }

        }
        if (menu.getMenuType()==1) {
            FirMenu firMenu = firMenuMapper.selectById(menu.getId());
            if (firMenu != null) {
                //使用 beanutils工具复制同一种结构
                BeanUtils.copyProperties(menu, firMenu);
//                firMenu.setName(menu.getName());
//                firMenu.setInfo(menu.getInfo());
//                firMenu.setCreatTime(menu.getCreatTime());
//                firMenu.setUpdateTime(menu.getUpdataTime());
                int update = firMenuMapper.update(firMenu, new LambdaQueryWrapper<FirMenu>()
                        .eq(menu.getId() != null, FirMenu::getId, menu.getId()));
                if (update > 0) {
                    return menu;
                }
            }
        }
        if (menu.getMenuType()==3) {
            SecMenu secMenu = secMenuMapper.selectById(menu.getId());
            if (secMenu != null) {
                BeanUtils.copyProperties(menu,secMenu);
//                secMenu.setName(menu.getName());
//                secMenu.setInfo(menu.getInfo());
//                secMenu.setCreatTime(menu.getCreatTime());
//                secMenu.setUpdateTime(menu.getUpdataTime());
                int update = secMenuMapper.update(secMenu, new LambdaQueryWrapper<SecMenu>()
                        .eq(menu.getId() != null, SecMenu::getId, menu.getId()));
                if (update > 0) {
                    return menu;
                }
            }
        }
        return null;

    }

    @Override
    public Page showMenuList(Integer pageNum,Integer pageSize) {
        Page page = new Page(pageNum, pageSize);
        menuMapper.selectPage(page, new QueryWrapper<Menu>(null));
        return page;
    }

    @Override
    public Page showFirMenuList(Integer pageNum, Integer pageSize, Long menuId) {
        List<MenuFirmenu> menuFirMenus = menuFirmenuMapper.selectList(new LambdaQueryWrapper<MenuFirmenu>()
                .eq(menuId != null, MenuFirmenu::getMenuId, menuId));
        if (!menuFirMenus.isEmpty()) { // null empty
            List<Long> firMenuIds = new ArrayList<>();
            for (MenuFirmenu menuFirmenu : menuFirMenus) {
                Long firMenuId = menuFirmenu.getFirmenuId();
                firMenuIds.add(firMenuId);
            }
            Page page = new Page<FirMenu>(pageNum, pageSize);
            List<FirMenu> firMenus = firMenuMapper.selectBatchIds(firMenuIds);
            page.setRecords(firMenus);
            page.setPages(firMenus.size());
            return page;
        }
        return null;
    }

    @Override
    public Page showSecMenuList(Integer pageNum, Integer pageSize, Long firMenuId) {
        List<FirmenuSecmenu> firmenuSecmenus = firmenuSecmenuMapper.selectList(
                new LambdaQueryWrapper<FirmenuSecmenu>().
                        eq(firMenuId != null, FirmenuSecmenu::getFirmenuId , firMenuId));
        if (firmenuSecmenus!=null) {
            Page page = new Page(pageNum, pageSize);
            ArrayList<Long> secMenuIds = new ArrayList<>();
            for (FirmenuSecmenu firmenuSecmenu : firmenuSecmenus) {
                Long secMenuId = firmenuSecmenu.getSecmenuId();
                secMenuIds.add(secMenuId);
            }
            List<SecMenu> secMenus = secMenuMapper.selectBatchIds(secMenuIds);
            page.setRecords(secMenus);
            page.setTotal(secMenus.size());
            return page;
        }
        return null;
    }


//    @Override
//    public boolean deleteMenuById(Long menuId, Integer type) {
//        //删除一级菜单
//        if (menuId != null && type == 1) {
//            //先删除一级菜单和角色表的关联
//            int delete = roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>()
//                    .eq(RoleMenu::getMenuId, menuId));
//            //有关联的话且删除关联成功（可能没有关联所以去掉if (delete > 0)）
//            //if (delete > 0) {
//            //获取一级菜单和二级菜单的关联记录，主要是为了获取二级菜单的id
//            List<MenuFirmenu> menuFirmenus = menuFirmenuMapper.selectList(new LambdaQueryWrapper<MenuFirmenu>()
//                    .eq(MenuFirmenu::getMenuId, menuId));
//            //获取记录以后可以直接删除关联关系
//            int menuFirMenuDelete = menuFirmenuMapper.delete(new LambdaQueryWrapper<MenuFirmenu>().eq(MenuFirmenu::getMenuId, menuId));
//            //如果一级菜单下面已经没有二级菜单了，那么删除记录数就会为0，就不继续执行下面代码
//            if (menuFirMenuDelete > 0) {
//                List<Long> firMenuIds = new ArrayList<>();
//                List<Long> secMenuIds = new ArrayList<>();
//                for (MenuFirmenu menuFirmenu : menuFirmenus) {
//                    Long firMenuId = menuFirmenu.getFirmenuId();
//                    firMenuIds.add(firMenuId);
//                    if (firMenuId != null) {
//                        List<FirmenuSecmenu> firmenuSecmenus = firmenuSecmenuMapper.selectList(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getFirmenuId, firMenuId));
//                        for (FirmenuSecmenu firmenuSecmenu : firmenuSecmenus) {
//                            Long secMenuId = firmenuSecmenu.getSecmenuId();
//                            secMenuIds.add(secMenuId);
//                            //取出三级菜单的secMenuId ,二级菜单和三级菜单的关联记录可以删除了
//                            int firMenuSecMenuDelete = firmenuSecmenuMapper.delete(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getFirmenuId, firMenuId));
//                            if (firMenuSecMenuDelete > 0) {
//                                //删除三级菜单
//                                secMenuMapper.deleteBatchIds(secMenuIds);
//                            }
//                        }
//                    }
//                }
//                //删除二级菜单
//                firMenuMapper.deleteBatchIds(firMenuIds);
//            }
//            //删除一级菜单
//            menuMapper.deleteById(menuId);
//            //}
//            return true;
//        }
//
//        if (menuId != null && type == 2) {
//            Long firMenuId = menuId;
//            List<Long> secMenuIds = new ArrayList<>();
//            menuFirmenuMapper.delete(new LambdaQueryWrapper<MenuFirmenu>().eq(MenuFirmenu::getFirmenuId, firMenuId));
//            List<FirmenuSecmenu> firmenuSecmenus = firmenuSecmenuMapper.selectList(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getFirmenuId, firMenuId));
//            for (FirmenuSecmenu firmenuSecmenu : firmenuSecmenus) {
//                Long secMenuId = firmenuSecmenu.getSecmenuId();
//                secMenuIds.add(secMenuId);
//            }
//
//            int delete = firmenuSecmenuMapper.delete(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getFirmenuId, firMenuId));
//            firMenuMapper.deleteById(firMenuId);
//            if (delete > 0) {
//                secMenuMapper.deleteBatchIds(secMenuIds);
//            }
//            return true;
//        }
//        if (menuId != null && type == 3) {
//            Long secMenuId = menuId;
//            firmenuSecmenuMapper.delete(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getSecmenuId, secMenuId));
//            secMenuMapper.deleteById(secMenuId);
//            return true;
//        }
//        return false;
//    }


//用户删除某一级菜单
//    @Override
//    public void deleteMenuById(Long menuId, Long firMenuId, Long secMenuId) {
//        if (menuId!=null && firMenuId==null&&secMenuId == null) {
//            int delete = roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, menuId));
//            if (delete > 0) {
//                menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getId,menuId));
//            }
//        }
//
//        if (menuId!=null&&firMenuId!=null&&menuId==null){
//            int roleMenuDelete = roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, menuId));
//            if (roleMenuDelete > 0) {
//                menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getId,menuId));
//                int menuFirMenuDelete = menuFirmenuMapper.delete(new LambdaQueryWrapper<MenuFirmenu>().eq(MenuFirmenu::getFirmenuId, firMenuId));
//                if (menuFirMenuDelete> 0) {
//                    menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getId,menuId));
//                    firMenuMapper.delete(new LambdaQueryWrapper<FirMenu>().eq(FirMenu::getId,firMenuId));
//                   }
//              }
//            }
//
//        if (menuId!=null&&firMenuId!=null&&secMenuId!=null){
//            int roleMenuDelete = roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, menuId));
//            if (roleMenuDelete > 0) {
//                int menuFirMenuDelete = menuFirmenuMapper.delete(new LambdaQueryWrapper<MenuFirmenu>().eq(MenuFirmenu::getFirmenuId, firMenuId));
//                if (menuFirMenuDelete> 0) {
//                    int delete = firmenuSecmenuMapper.delete(new LambdaQueryWrapper<FirmenuSecmenu>().eq(FirmenuSecmenu::getSecmenuId, secMenuId));
//                    if (delete > 0) {
//                        menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getId,menuId));
//                        firMenuMapper.delete(new LambdaQueryWrapper<FirMenu>().eq(FirMenu::getId,firMenuId));
//                        secMenuMapper.delete(new LambdaQueryWrapper<SecMenu>().eq(SecMenu::getId,secMenuId));
//                    }
//                }
//            }
//        }
//    }


}
