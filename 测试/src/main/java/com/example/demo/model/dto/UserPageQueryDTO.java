package com.example.demo.model.dto;

import com.example.demo.common.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * 用户分页查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageQueryDTO extends PageParam {
    /**
     * 用户名搜索关键词
     */


    private String userName;
    
    /**
     * 邮箱搜索关键词
     */
    private String email;

//    /**
//     * 当前页码
//     */
//    private Integer current = 1;
//
//    /**
//     * 每页显示条数
//     */
//    private Integer size = 10;
} 