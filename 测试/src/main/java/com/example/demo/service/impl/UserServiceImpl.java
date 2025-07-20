package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.dto.GetUserByIdDTO;
import com.example.demo.model.dto.UserPageQueryDTO;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    
    /**
     * 分页查询用户列表
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<UserListItemVO> pageUsers(UserPageQueryDTO queryDTO) {
        try {
            // 1. 构建查询条件
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            
            // 2. 添加条件过滤
            if (StringUtils.hasText(queryDTO.getUserName())) {
                queryWrapper.like(User::getUserName, queryDTO.getUserName());
            }
            
            if (StringUtils.hasText(queryDTO.getEmail())) {
                queryWrapper.like(User::getEmail, queryDTO.getEmail());
            }
            
            // 3. 创建分页对象并执行查询
            Page<User> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
            Page<User> userPage = this.page(page, queryWrapper);

            // 4. 使用Stream API将User实体转换为VO对象
            List<UserListItemVO> voList = userPage.getRecords().stream()
                    .map(user -> UserListItemVO.builder()
                            .id(user.getId())
                            .userName(user.getUserName())
                            .email(user.getEmail())
                            .build())
                    .collect(Collectors.toList());
            
            // 5. 使用PageResult.success方法创建分页结果
            return PageResult.success(userPage, voList);
        } catch (Exception e) {
            return PageResult.error("查询用户列表失败：" + e.getMessage());
        }
    }
}