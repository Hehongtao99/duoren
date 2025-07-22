package com.example.demo.model.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private Long id;
    private String userName;
    private String email;
}
