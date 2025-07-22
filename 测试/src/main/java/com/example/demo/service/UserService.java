package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.model.dataobject.User;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UpdateUserDTO;
import com.example.demo.model.dto.DeleteUserDTO;
import com.example.demo.model.dto.UserPageQueryDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UserListItemVO;

import java.util.List;

public interface UserService extends IService<User> {


}