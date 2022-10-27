package com.csm.myproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.Utils.Base64Util;
import com.csm.myproject.Utils.FileToBytes;
import com.csm.myproject.entity.Role;
import com.csm.myproject.entity.User;
import com.csm.myproject.entity.UserRole;
import com.csm.myproject.mapper.RoleMapper;
import com.csm.myproject.mapper.UserMapper;
import com.csm.myproject.mapper.UserRoleMapper;
import com.csm.myproject.service.IFirmenuSecmenuService;
import com.csm.myproject.service.IUserRoleService;
import com.csm.myproject.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean deleteUserRer(long userId) {

        LambdaQueryWrapper<UserRole> queryWrap = new LambdaQueryWrapper<>();
        queryWrap.eq(UserRole ::getUserId,userId);
        userRoleMapper.delete(queryWrap);
        userMapper.deleteById(userId);

        return false;
    }

    @Override
    public Page<Role> getUserRoles(long userId,Integer pageNum,Integer pageSize) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole ::getUserId,userId);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        List<Long> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            Long roleId = userRole.getRoleId();
            roleIds.add(roleId);
        }
        List<Role> roles = roleMapper.selectBatchIds(roleIds);
        Page<Role> page = new Page<>(pageNum,pageSize);
        page.setRecords(roles);

        return page;
    }

    @Override
    public boolean updateUser(User user) {
        if (userMapper.selectById(user.getId())!=null) {
            int update = userMapper.update(user, new LambdaQueryWrapper<User>().eq(user.getId() != null, User::getId, user.getId()));
            if (update > 0) {
                return true;
            }
        }
        return false;
    }
    public User insertUser(MultipartFile file,User user) {
        if (user!=null&&file!=null){
            BASE64Encoder encoder = new BASE64Encoder();
            try {

                String firStr = encoder.encode(file.getBytes());
                user.setAvatar(firStr);
                String path = "F\\:" + user.getUsername() + System.currentTimeMillis() + ".png";
                Base64Util.GenerateImage(user.getAvatar(), path);
                File avatarFile = new File(path);
                if (userMapper.insert(user)>0) {
                    return user;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
}

    @Override
    public User updateAvatar(MultipartFile file, Long userId) {
        BASE64Encoder encoder = new BASE64Encoder();
        if (file!=null&&userId!=null){
            User user = userMapper.selectById(userId);

            try {
                String firStr = encoder.encode(file.getBytes());
                user.setAvatar(firStr);

                String path = "F\\:" + user.getUsername() + System.currentTimeMillis() + ".png";
                Base64Util.GenerateImage(user.getAvatar(), path);
                File avatarFile = new File(path);
                if (userMapper.update(user,new LambdaQueryWrapper<User>().eq(userId!=null,User::getId,userId))>0) {
                    return user;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    @Override
    public boolean findUserByName(String name) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(name != null, User::getUsername, name));
        if (user!=null) {
            return true;
        }
        return false;
    }
}
