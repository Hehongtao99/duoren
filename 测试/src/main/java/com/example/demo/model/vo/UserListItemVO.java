package com.example.demo.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用户列表项VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListItemVO {
    private Long id;

    private String userName;

    private String email;

    private String address;

    private int age;

    private BigDecimal heightCm;

    private BigDecimal weightKg;

    private BigDecimal gpa ;
} 