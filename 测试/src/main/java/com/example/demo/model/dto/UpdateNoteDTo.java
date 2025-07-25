package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNoteDTo {
    @NotBlank(message = "标题不能为空")
    private String noteName;

    @NotBlank(message = "内容不能为空")
    private String noteContent;

    @NotBlank(message = "分类不能为空")
    private String categoryName;

    @NotBlank(message = "作者不能为空")
    private String username;

    private List<@NotBlank String> tagNames;
}
