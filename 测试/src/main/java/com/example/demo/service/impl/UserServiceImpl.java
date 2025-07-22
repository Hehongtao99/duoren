package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dataobject.User;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UpdateUserDTO;
import com.example.demo.model.dto.DeleteUserDTO;

import com.example.demo.model.dto.UserPageQueryDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // All basic CRUD operations are provided by ServiceImpl
    
    @Autowired
    private UserMapper userMapper;


}