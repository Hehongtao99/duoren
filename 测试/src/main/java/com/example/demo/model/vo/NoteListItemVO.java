package com.example.demo.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteListItemVO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 笔记名称
     */

    private String noteName;

    /**
     * 创建用户ID
     */

//    private Integer Id;

    private String userName;
//    /**
//     * 内容详情ID
//     */
//
//    private Integer contentDetailId;

//    /**
//     * 分类ID
//     */
//
//    private Integer categoryId;

    private String categoryName;
    /**
     * 创建时间
     */

    private List<String> tagNames;

    private LocalDateTime createTime;

//    /**
//     * 更新时间
//     */
//
//    private LocalDateTime updateTime;
}
