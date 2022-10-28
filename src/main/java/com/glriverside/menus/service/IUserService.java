package com.glriverside.menus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glriverside.menus.entity.Role;
import com.glriverside.menus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
      boolean insertUser(MultipartFile file, User user);
      User updateAvatar(MultipartFile file,Long userId);
      boolean findUserByName(String name);
}
