package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.model.dataobject.User;
import com.example.demo.model.dto.DeleteUserDTO;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UpdateUserDTO;

import com.example.demo.model.dto.UserPageQueryDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UpdateUserVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.common.Result;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public Result<List<UserListItemVO>> getAllUsers(){
        return userService.userlistitem();

    }
    @GetMapping("/getUserById")
    public Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO){
        return userService.getUserById(getUserByIdDTO);

    }
    @GetMapping("/getUserNameById")
    public Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO){
        return userService.getUserNameById(getUserByIdDTO);
    }
    @PutMapping("/update")
    public Result<UpdateUserVO> updateUser(UpdateUserDTO updateUserDTO){
        return userService.updateUser(updateUserDTO);
    }
    @DeleteMapping("/delete")
    public  boolean deleteUser(DeleteUserDTO deleteUserDTO){
        return userService.deleteUser(deleteUserDTO);
    }




}