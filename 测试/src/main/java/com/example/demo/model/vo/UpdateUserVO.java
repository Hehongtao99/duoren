package com.example.demo.model.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserVO {
    private Long userId;
    private String userName;
    private String email;
}
