package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dataobject.User;

import com.example.demo.model.dto.*;
import com.example.demo.model.vo.GetUserByIdVO;
import com.example.demo.model.vo.GetUserNameByIdVO;
import com.example.demo.model.vo.UserListItemVO;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // All basic CRUD operations are provided by ServiceImpl

    @Autowired
    private UserMapper userMapper;


    @Override
    public Result<List<UserListItemVO>> userlistitem() {
        List<User> users = userMapper.selectList(null);
        List<UserListItemVO> collect = users.stream().map(user ->
                        UserListItemVO.builder()
                        .id(user.getId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .age(user.getAge())
                        .heightCm(user.getHeightCm())
                        .weightKg(user.getWeightKg())
                        .gpa(user.getGpa())
                        .build())
                .collect(Collectors.toList());

        if(collect.isEmpty()){
            throw new BizException(ResponseCodeEnum.SYSTEM_ERROR);
        }

        return Result.success(collect);
    }

    @Override
    public Result<GetUserByIdVO> getUserById(GetUserByIdDTO getUserByIdDTO) {
        User user = userMapper.selectById(getUserByIdDTO.getId());

        GetUserByIdVO getUserByIdVO1 = new GetUserByIdVO();
        BeanUtils.copyProperties(user,getUserByIdVO1);

//        GetUserByIdVO getUserByIdVO = GetUserByIdVO.builder()
//                .Id(user.getId())
//                .userName(user.getUserName())
//                .email(user.getEmail())
//                .build();

        return Result.success(getUserByIdVO1);
    }

    @Override
    public Result<GetUserNameByIdVO> getUserNameById(GetUserByIdDTO getUserByIdDTO) {
        User user = userMapper.selectNameById(getUserByIdDTO.getId());
        GetUserNameByIdVO getUserNameByIdVO = GetUserNameByIdVO.builder().userName(user.getUserName()).build();
        return Result.success(getUserNameByIdVO);
    }

    @Override
    public Result<?> updateUser(UpdateUserDTO updateUserDTO) {
//        User user = new User();
//        user.setId(updateUserDTO.getId());
//        user.setUserName(updateUserDTO.getUserName());
//        user.setEmail(updateUserDTO.getEmail());
//
//        userMapper.updateById(user);
//
//        UpdateUserVO updateUserVO = UpdateUserVO.builder()
//                .Id(user.getId())
//                .userName(user.getUserName())
//                .email(user.getEmail())
//                .build();
//
//        return Result.success(updateUserVO);

        Long Id = updateUserDTO.getId();
        User user = userMapper.selectById(Id);

        if(Objects.isNull(user)){
            throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
        }

        user.setUserName(updateUserDTO.getUserName());
        user.setEmail(updateUserDTO.getEmail());
        //mybatis
        //先删除，再新增21
        userMapper.updateById(user);
        return Result.success();
    }

    @Override
    //开启事务
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(DeleteUserDTO deleteUserDTO) {

        Long Id = deleteUserDTO.getId();
        User user = userMapper.selectById(Id);
        if(Objects.isNull(user)){
            throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        return userMapper.deleteById(deleteUserDTO.getId())>0;
    }

    @Override
    public PageResult<UserListItemVO> getUserByPage(UserPageQueryDTO userPageQueryDTO) {
        Integer current = userPageQueryDTO.getCurrent();
        Integer size = userPageQueryDTO.getSize();
        String userName = userPageQueryDTO.getUserName();
        String email = userPageQueryDTO.getEmail();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(userName), User::getUserName, userName)
                .like(StrUtil.isNotBlank(email), User::getEmail, email)
                ;

        Page<User> Page = new Page<>(current, size);

        Page<User> userPage = userMapper.selectPage(Page, wrapper);
        if(Objects.isNull(userPage)){
            throw new BizException(ResponseCodeEnum.PAGE_NOT_FOUND);
        }

        List<UserListItemVO> collect = userPage.getRecords().stream().
                map(user -> UserListItemVO.builder()
                        .id(user.getId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .age(user.getAge())
                        .heightCm(user.getHeightCm())
                        .weightKg(user.getWeightKg())
                        .gpa(user.getGpa())
                        .build())
                .toList();

        return PageResult.success(userPage,collect);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> AddUser(AddUserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);

        try {
            int insert = userMapper.insert(user);
            return Result.success();
        }catch (Exception e){
            log.error("添加失败");
        }


        return Result.success();
    }
}