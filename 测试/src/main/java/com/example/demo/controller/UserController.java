package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.model.User;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UserPageQueryDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import com.example.demo.common.Result;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.list();
        return Result.success(users);
    }

    @GetMapping("/getUserById")
    public Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO){
        return userService.getUserById(getUserByIdDTO);
    }

    //通过用户id获得用户名称
    @GetMapping("/getUserNameById")
    public Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO){
        return userService.getUserNameById(getUserByIdDTO);
    }

    @GetMapping("/page")
    public PageResult<UserListItemVO> getPageList(UserPageQueryDTO dto){
        return userService.pageUsers(dto);
    }


}