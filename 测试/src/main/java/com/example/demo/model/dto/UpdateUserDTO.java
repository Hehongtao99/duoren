package com.example.demo.model.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private Long userId;
    private String userName;
    private String email;
}
