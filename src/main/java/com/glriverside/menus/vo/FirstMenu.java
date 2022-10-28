package com.glriverside.menus.vo;

import com.glriverside.menus.entity.FirMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 快乐小柴
 * @Date 2022/10/28 18:25
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstMenu extends FirMenu {
    List<SecondMenu> secondMenuList;
}
