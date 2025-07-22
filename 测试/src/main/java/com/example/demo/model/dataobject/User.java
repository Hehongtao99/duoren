package com.example.demo.model.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("users")
public class User {
    //
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @TableField("username")
    private String userName;

    @TableField("email")
    private String email;

    @TableField("address")
    private String address;

    @TableField("age")
    private int age;

    @TableField("height_cm")
    private BigDecimal heightCm;

    @TableField("weight_kg")
    private BigDecimal weightKg;

    @TableField("gpa")
    private BigDecimal gpa ;
} 