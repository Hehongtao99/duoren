package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.model.dto.*;

import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
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
    @GetMapping("/detail/{Id}")
    public Result<GetUserByIdVO> getUserById(@PathVariable Long Id){
        GetUserByIdDTO getUserByIdDTO = new GetUserByIdDTO();
        getUserByIdDTO.setId(Id);
        return userService.getUserById(getUserByIdDTO);
    }
    @GetMapping("/getUserNameById")
    public Result<GetUserNameByIdVO> getUserNameById(@RequestParam Long Id){
        GetUserByIdDTO getUserByIdDTO = new GetUserByIdDTO();
        getUserByIdDTO.setId(Id);
        return userService.getUserNameById(getUserByIdDTO);
    }
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody  UpdateUserDTO updateUserDTO){
        return userService.updateUser(updateUserDTO);
    }

    @DeleteMapping("/delete/{Id}")
    public  boolean deleteUser(@PathVariable Long Id){
        DeleteUserDTO deleteUserDTO = new DeleteUserDTO();
        deleteUserDTO.setId(Id);
        return userService.deleteUser(deleteUserDTO);
    }

    @GetMapping("/page")
    public PageResult<UserListItemVO> getUserByPage(UserPageQueryDTO userPageQueryDTO) {
        return userService.getUserByPage(userPageQueryDTO);
    }

    @PostMapping("/insert")
    public Result<?> Add(@RequestBody AddUserDTO dto){
        return userService.AddUser(dto);
    }
}