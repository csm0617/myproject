package com.glriverside.menus.mapper;

import com.glriverside.menus.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glriverside.menus.vo.ZeroMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<ZeroMenu> getAllMenus(Long RoleId);
}
