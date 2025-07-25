package com.example.demo.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDetailVO {
    private String noteContent;

    private String categoryName;

    private String userName;

    private List<String> tagNames;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
