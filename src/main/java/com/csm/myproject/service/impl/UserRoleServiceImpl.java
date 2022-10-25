package com.csm.myproject.service.impl;

import com.csm.myproject.entity.User;
import com.csm.myproject.entity.UserRole;
import com.csm.myproject.mapper.UserRoleMapper;
import com.csm.myproject.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
