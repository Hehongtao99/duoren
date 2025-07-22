package com.example.demo.model.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private Long UserId;
    private String userName;
    private String email;
}
