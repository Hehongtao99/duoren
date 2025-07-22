package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dataobject.User;

import com.example.demo.model.dto.DeleteUserDTO;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UpdateUserDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UpdateUserVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // All basic CRUD operations are provided by ServiceImpl

    @Autowired
    private UserMapper userMapper;


    @Override
    public Result<List<UserListItemVO>> userlistitem() {
        List<User> users = userMapper.selectList(null);
        List<UserListItemVO> collect = users.stream().map(user -> UserListItemVO.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .age(user.getAge())
                        .heightCm(user.getHeightCm())
                        .weightKg(user.getWeightKg())
                        .gpa(user.getGpa())
                        .build())
                .collect(Collectors.toList());
        return Result.success(collect);
    }

    @Override
    public Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO) {
        User user = userMapper.selectById(getUserByIdDTO.getUserId());

        GetUserByIdVO getUserByIdVO = GetUserByIdVO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();

        return Result.success(getUserByIdVO);
    }

    @Override
    public Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO) {
        User user = userMapper.selectNameById(getUserByIdDTO.getUserId());
        GetUserNameByIdVO getUserNameByIdVO = GetUserNameByIdVO.builder().userName(user.getUserName()).build();


        return Result.success(getUserNameByIdVO);
    }

    @Override
    public Result<UpdateUserVO> updateUser(UpdateUserDTO updateUserDTO) {
        User user = new User();
        user.setUserId(updateUserDTO.getUserId());
        user.setUserName(updateUserDTO.getUserName());
        user.setEmail(updateUserDTO.getEmail());

        userMapper.updateById(user);

        UpdateUserVO updateUserVO = UpdateUserVO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();

        return Result.success(updateUserVO);
    }

    @Override
    public boolean deleteUser(DeleteUserDTO deleteUserDTO) {
        return userMapper.deleteById(deleteUserDTO.getUserId())>0;
    }
}