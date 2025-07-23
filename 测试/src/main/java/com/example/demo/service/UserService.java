package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.model.dataobject.User;
import com.example.demo.model.dto.*;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UpdateUserVO;
import com.example.demo.model.vo.UserListItemVO;

import java.util.List;

public interface UserService extends IService<User> {

    Result<List<UserListItemVO>> userlistitem();

    Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO);

    Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO);

    Result<?> updateUser(UpdateUserDTO updateUserDTO);

    boolean deleteUser(DeleteUserDTO deleteUserDTO);

    PageResult<UserListItemVO> getUserByPage(UserPageQueryDTO userPageQueryDTO);

    Result<?> AddUser(AddUserDTO dto);
}