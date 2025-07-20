package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.User;
import com.example.demo.model.vo.GetUserNameByIdVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    //jdk17之后，引入了default
    default User selectNameById(Long id){
        //条件构造器，用来构造条件的
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,id);
        //where id = "1"

        return this.selectOne(wrapper);
    }

}