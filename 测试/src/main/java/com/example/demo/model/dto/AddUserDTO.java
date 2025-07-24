package com.example.demo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserDTO {

    private String userName;

    private String email;


    private String address;

    private int age;


    private BigDecimal heightCm;

    private BigDecimal weightKg;


    private BigDecimal gpa ;
}
