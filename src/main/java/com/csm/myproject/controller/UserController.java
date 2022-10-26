package com.csm.myproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csm.myproject.entity.User;
import com.csm.myproject.entity.UserRole;
import com.csm.myproject.exception.AppException;
import com.csm.myproject.exception.AppExceptionCodeMsg;
import com.csm.myproject.mapper.UserMapper;
import com.csm.myproject.response.Response;
import com.csm.myproject.service.IUserService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
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
    @GetMapping("/all")
    public Response<Page<User>> getAllUser(@Parameter(description = "第几页，默认为1") @RequestParam(defaultValue = "1") Integer pageNum,
                                           @Parameter(description = "每页显示的数量，默认为10") @RequestParam(defaultValue = "10") Integer pageSize){
        Page<User> page = new Page(pageNum, pageSize);
        userMap.selectPage(page,null);
        return Response.ok(page);
    }

    @GetMapping
    public Response<Page<User>> getUsersByInfo(@Parameter(description = "第几页，默认为1") @RequestParam(defaultValue = "1") Integer pageNum,
                                               @Parameter(description = "每页显示的数量，默认为10") @RequestParam(defaultValue = "10") Integer pageSize,
                                               @Parameter(description = "姓名")@RequestParam String name,
                                               @Parameter(description = "手机号")@RequestParam String phone) {
        Page<User> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),User::getUsername,name).
                like(StringUtils.isNotEmpty(phone),User::getPhone,phone);
        Page<User> userPage = userMap.selectPage(page, queryWrapper);
        return Response.ok(userPage);
    }
    @GetMapping("/roles")
    public Response getUserRole(@Parameter(description = "第几页，默认为1")@RequestParam(defaultValue = "1") Integer pageNum,
                                @Parameter(description = "每页显示的数量，默认为10")@RequestParam(defaultValue = "10") Integer pageSize,
                                @Parameter(description = "用户id")@RequestParam Long userId){

        Page<UserRole> page = userService.getUserRoles(userId, pageNum, pageSize);

        return Response.ok(page);

    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public Response<Boolean> deleteUserById(@Parameter(description = "用户id")@PathVariable Long id){

        try {
            userService.deleteUserRer(id);
        } catch (Exception e) {
            throw new AppException(AppExceptionCodeMsg.DELETE_ERR_MSG);
        }
        return Response.success("成功删除用户",true);
    }

    @PutMapping
    public Response<Boolean> updateUser(@RequestBody User user){
        if (userService.updateUser(user)!=null){
            return Response.success("修改用户成功",true);
        }
        return Response.error(AppExceptionCodeMsg.UPDATE_ERR_MSG);
    }
    @PostMapping(headers="content-type=multipart/form-data")
    public Response<Boolean> insertUser(@Parameter(description = "上传用户头像")@RequestPart("file") MultipartFile file,
                                        @RequestBody User user){
        if(userService.insertUser(file, user)!=null){
            return Response.success("成功上传",true);
        }
        return Response.error(AppExceptionCodeMsg.UPLOAD_AVATAR_ERR_MSG);
    }

    @PutMapping (value="/avatar",headers="content-type=multipart/form-data")
    public Response<Boolean> updateAvatar(@Parameter(description = "上传需要修改的用户头像")@RequestPart("file") MultipartFile file,
                                          @Parameter(description = "用户id") @RequestParam Long userId){
        if(userService.updateAvatar(file,userId)!=null){
            return Response.success("成功修改用户头像",true);
        }
            return Response.error(AppExceptionCodeMsg.UPLOAD_AVATAR_ERR_MSG);
    }

}
