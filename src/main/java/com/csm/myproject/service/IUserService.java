package com.csm.myproject.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.Role;
import com.csm.myproject.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csm.myproject.entity.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Repository
public interface IUserService extends IService<User> {
      boolean deleteUserRer(long userId);
      Page<Role> getUserRoles(long userId, Integer pageNumber, Integer pageSize);
      boolean updateUser(User user);
      User insertUser(MultipartFile file, User user);
      User updateAvatar(MultipartFile file,Long userId);
      boolean findUserByName(String name);
}
