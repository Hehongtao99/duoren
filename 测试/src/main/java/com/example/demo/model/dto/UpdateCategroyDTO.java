package com.example.demo.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data

public class UpdateCategroyDTO {
    private Integer categoryId;

    private String categoryName;
}
