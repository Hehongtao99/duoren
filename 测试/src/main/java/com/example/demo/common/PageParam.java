package com.example.demo.common;

import lombok.Data;

/**
 * 分页参数基类
 */
@Data
public class PageParam {
    /**
     * 当前页码
     */
    private Integer current = 1;
    
    /**
     * 每页显示条数
     */
    private Integer size = 10;
} 