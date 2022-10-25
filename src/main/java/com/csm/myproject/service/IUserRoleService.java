package com.csm.myproject.service;

import com.csm.myproject.entity.User;
import com.csm.myproject.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csm.myproject.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Repository
public interface IUserRoleService extends IService<UserRole> {


}

