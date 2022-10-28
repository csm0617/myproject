package com.glriverside.menus.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glriverside.menus.entity.Role;
import com.glriverside.menus.entity.User;
import com.glriverside.menus.exception.AppException;
import com.glriverside.menus.exception.AppExceptionCodeMsg;
import com.glriverside.menus.mapper.UserMapper;
import com.glriverside.menus.response.Response;
import com.glriverside.menus.result.user.UserData;
import com.glriverside.menus.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author csm
 * @since 2022-10-20
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMap;

    @ApiOperation(value = "获取所有的用户并分页展示")
    @GetMapping("/all")
    public Response<Page<UserData>> getAllUser(@ApiParam(value = "第几页，默认为1") @RequestParam(defaultValue = "1") Integer pageNum,
                                               @ApiParam(value = "每页显示的数量，默认为10") @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<UserData> page = new Page(pageNum, pageSize);
        List<User> users = userMap.selectList(null);
        List<UserData> userDataList = new ArrayList<>();
        for (User user : users) {
            UserData userData = new UserData();
            BeanUtils.copyProperties(user, userData);
            userDataList.add(userData);
        }
        page.setRecords(userDataList);
        return Response.ok(page);
    }

    @ApiOperation(value = "通过用户信息来查询用户")
    @GetMapping
    public Response<Page<SecurityProperties.User>> getUsersByInfo(@ApiParam(value = "第几页，默认为1") @RequestParam(defaultValue = "1") Integer pageNum,
                                                                  @ApiParam(value = "每页显示的数量，默认为10") @RequestParam(defaultValue = "10") Integer pageSize,
                                                                  @ApiParam(value = "姓名") @RequestParam(required = false) String name,
                                                                  @ApiParam(value = "手机号") @RequestParam(required = false) String phone) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name), User::getUsername, name).
                like(StringUtils.isNotEmpty(phone), User::getPhone, phone);
        Page<User> userPage = userMap.selectPage(page, queryWrapper);
        return Response.ok(userPage);
    }


    @ApiOperation(value = "通过用户id来查询该用户的角色")
    @GetMapping("/roles")
    public Response<Page<Role>> getUserRole(@ApiParam(value = "第几页，默认为1") @RequestParam(defaultValue = "1") Integer pageNum,
                                            @ApiParam(value = "每页显示的数量，默认为10") @RequestParam(defaultValue = "10") Integer pageSize,
                                            @ApiParam(value = "用户id") @RequestParam Long userId) {

        Page<Role> page = userService.getUserRoles(userId, pageNum, pageSize);

        return Response.ok(page);

    }

    @ApiOperation(value = "通过用户id来删除用户")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public Response<Boolean> deleteUserById(@ApiParam(value = "用户id") @PathVariable Long id) {

        try {
            userService.deleteUserRer(id);
        } catch (Exception e) {
            throw new AppException(AppExceptionCodeMsg.DELETE_ERR_MSG);
        }
        return Response.success("成功删除用户", true);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping
    public Response<Boolean> updateUser(@ApiParam(value = "请传入用户信息") @RequestBody User user) {
        if (userService.updateUser(user)) {
            return Response.success("修改用户成功", true);
        }
        return Response.error(AppExceptionCodeMsg.UPDATE_ERR_MSG);
    }

//    @ApiOperation(value = "新增用户")
//    @PostMapping()
//    public Response<Boolean> insertUser(@ApiParam(value = "传入用户") @RequestBody User user){
//        if(!userService.findUserByName(user.getUsername())) {
//            if (userMap.insert(user) > 0) {
//                return Response.success("新增用户成功", true);
//            }
//            return Response.error(AppExceptionCodeMsg.INSERT_ERR_MSG);
//        }else {
//            throw new AppException(AppExceptionCodeMsg.USER_ALREADY_EXISTS_MSG);
//        }
//    }

    /**
     * 因为同时上传头像和json对象的时候的，
     * content-type会比requestBody的优先级高，
     * 导致user不能以json的形式，
     * 所以把user的属性以@requestParam的形式传进去
     */
    @ApiOperation(value = "新增用户")
    @PostMapping()
    public Response<Boolean> insertUser(@ApiParam(value = "上传用户头像") @RequestPart MultipartFile file,
                                        @ApiParam(value = "用户名") @RequestParam String username,
                                        @ApiParam(value = "密码") @RequestParam String pwd,
                                        @ApiParam(value = "手机号码") @RequestParam(required = false) String phone,
                                        @ApiParam(value = "生日") @RequestParam(required = false) LocalDate birth,
                                        @ApiParam(value = "邮箱") @RequestParam(required = false) String email) {
        User user = new User();
        user.setUsername(username);
        user.setPwd(pwd);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirth(birth);
        if (userService.insertUser(file,user)){
            return Response.success("成功添加用户",true);
        }else {
            return Response.error(AppExceptionCodeMsg.UPLOAD_AVATAR_ERR_MSG);
        }
    }

    @ApiOperation(value = "通过用户id来修改用户头像")
    @PutMapping(value = "/avatar", headers = "content-type=multipart/form-data")
    public Response<Boolean> updateAvatar(@ApiParam(value = "上传需要修改的用户头像") @RequestPart("file") MultipartFile file,
                                          @ApiParam(value = "用户id") @RequestParam Long userId) {
        if (userService.updateAvatar(file, userId) != null) {
            return Response.success("成功修改用户头像", true);
        }
        return Response.error(AppExceptionCodeMsg.UPLOAD_AVATAR_ERR_MSG);
    }

}
