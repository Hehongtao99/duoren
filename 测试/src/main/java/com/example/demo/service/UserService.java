package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Result;
import com.example.demo.model.User;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;

public interface UserService extends IService<User> {

    //根据用户id查询用户的所有信息
    Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO);

    Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO);
}