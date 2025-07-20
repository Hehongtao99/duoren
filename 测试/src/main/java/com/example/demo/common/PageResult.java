package com.example.demo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果基类
 */
@Data
public class PageResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Long pages;
    
    /**
     * 当前页码
     */
    private Long current;
    
    /**
     * 每页显示条数
     */
    private Long size;
    
    /**
     * 数据列表
     */
    private List<T> records;
    
    /**
     * 从MyBatis-Plus的Page对象创建成功的分页结果
     * @param page MyBatis-Plus的Page对象
     * @param records 记录列表
     * @return PageResult对象
     */
    public static <T, E> PageResult<T> success(Page<E> page, List<T> records) {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setRecords(records);
        return result;
    }
    
    /**
     * 从MyBatis-Plus的Page对象创建成功的分页结果，并使用转换函数将E类型转换为T类型
     * @param page MyBatis-Plus的Page对象
     * @param converter 将E类型转换为T类型的函数
     * @return PageResult对象
     */
    public static <T, E> PageResult<T> success(Page<E> page, Function<E, T> converter) {
        List<T> records = page.getRecords().stream()
                .map(converter)
                .collect(Collectors.toList());
        
        return success(page, records);
    }
    
    /**
     * 创建成功的分页结果
     */
    public static <T> PageResult<T> success(List<T> records, Long total, Long pages, Long current, Long size) {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setTotal(total);
        result.setPages(pages);
        result.setCurrent(current);
        result.setSize(size);
        result.setRecords(records);
        return result;
    }
    
    /**
     * 创建空的分页结果
     */
    public static <T> PageResult<T> empty() {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setTotal(0L);
        result.setPages(0L);
        result.setCurrent(1L);
        result.setSize(10L);
        result.setRecords(Collections.emptyList());
        return result;
    }
    
    /**
     * 创建失败的分页结果
     */
    public static <T> PageResult<T> error(String message) {
        PageResult<T> result = new PageResult<>();
        result.setCode(500);
        result.setMessage(message);
        result.setTotal(0L);
        result.setPages(0L);
        result.setCurrent(1L);
        result.setSize(10L);
        result.setRecords(Collections.emptyList());
        return result;
    }
    
    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
} 