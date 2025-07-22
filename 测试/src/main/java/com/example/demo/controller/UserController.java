package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.model.dataobject.User;
import com.example.demo.model.dto.DeleteUserDTO;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UpdateUserDTO;

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




}