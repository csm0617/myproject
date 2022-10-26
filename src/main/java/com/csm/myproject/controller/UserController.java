package com.csm.myproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.User;
import com.csm.myproject.entity.UserRole;
import com.csm.myproject.mapper.UserMapper;
import com.csm.myproject.response.Response;
import com.csm.myproject.service.IUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@RestController
@RequestMapping("/myproject/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMap;
    @GetMapping("/{pageNum}/{pageSize}")
    public Response<Page<User>> getAllUser(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        Page<User> page = new Page(pageNum, pageSize);
        userMap.selectPage(page,null);
        return Response.ok(page);
    }

    @GetMapping
    public Response<Page<User>> getUsersByInfo(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam String name,
                                     @RequestParam String phone) {
        Page<User> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),User::getUsername,name).
                like(StringUtils.isNotEmpty(phone),User::getPhone,phone);
        Page<User> userPage = userMap.selectPage(page, queryWrapper);
        return Response.ok(userPage);
    }
    @GetMapping("/roles")
    public Page<UserRole> getUserRole(@RequestParam Long userId,
                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam Integer pageSize
                                      ){

        Page<UserRole> page = userService.getUserRoles(userId, pageNum, pageSize);

        return page;

    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){

        try {
            userService.deleteUserRer(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping
    public boolean updateUser(@RequestBody User user){
        if (userService.updateUser(user)!=null){
            return true;
        }
        return false;
    }
    @PostMapping(headers="content-type=multipart/form-data")
    public boolean insertUser(@RequestPart("file") MultipartFile file,
                              @RequestBody User user){
        if(userService.insertUser(file, user)!=null){
            return true;
        }
        return false;
    }

    @PutMapping (value="/avatar",headers="content-type=multipart/form-data")
    public boolean updateAvatar(@RequestPart("file") MultipartFile file,
                                @RequestParam Long userId){
        if(userService.updateAvatar(file,userId)!=null){
            return true;
        }
            return false;
    }

}
