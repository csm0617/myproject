package com.csm.myproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 快乐小柴
 * @Date 2022/10/22 14:14
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private String name;
    private int type;
    private List<MenuItem> sub;
}
