package com.csm.myproject.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csm.myproject.vo.MenuItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Transactional
@Repository
public interface IMenuService extends IService<Menu> {
    List<MenuItem> showMenuListByRoleId(Long roleId);
    boolean deleteMenuById(Long menuId,Integer type);
    Menu insertMenu(Menu menu);
    Menu updateMenu(Menu menu);
//    List<MenuItem> showMenuList();
    Page showMenuList(Integer pageNum,Integer pageSize);
    Page showFirMenuList(Integer pageNum,Integer pageSize,Long menuId);
    Page showSecMenuList(Integer pageNum,Integer pageSize,Long firMenuId);
}
