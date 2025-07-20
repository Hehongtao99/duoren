package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // All basic CRUD operations are provided by ServiceImpl
    
    @Autowired
    private UserMapper userMapper;


    @Override
    public Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO) {
        //1.整理前端传来的查询数据
        Long id = getUserByIdDTO.getId();

        //2.数据库的查询
        User user = userMapper.selectById(id);

        //3.整理查询的数据变成vo返回
        GetUserByIdVO getUserByIdVO = GetUserByIdVO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();

        return Result.success(getUserByIdVO);
    }

    //通过用户id获得用户名称
    @Override
    public Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO) {
        Long id = getUserByIdDTO.getId();

        User user = userMapper.selectNameById(id);

        String userName = user.getUserName();
        GetUserNameByIdVO getUserNameByIdVO = new GetUserNameByIdVO();
        getUserNameByIdVO.setUserName(userName);

        return Result.success(getUserNameByIdVO);
    }
}