package com.example.demo.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("users")
public class User {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String userName;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 身高(厘米)
     */
    @TableField("height_cm")
    private BigDecimal heightCm;

    /**
     * 体重(公斤)
     */
    @TableField("weight_kg")
    private BigDecimal weightKg;

    /**
     * 绩点
     */
    @TableField("gpa")
    private BigDecimal gpa;



    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 (0-未删除, 1-已删除)
     */
    @TableLogic(value = "0", delval = "1")
    @TableField("deleted")
    private Integer deleted;
}