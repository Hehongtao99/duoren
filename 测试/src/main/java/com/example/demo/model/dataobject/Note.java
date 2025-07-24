package com.example.demo.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 笔记表 DO对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("note")
public class Note {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记名称
     */
    @TableField("note_name")
    private String noteName;

    /**
     * 创建用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 内容详情ID
     */
    @TableField("content_detail_id")
    private Integer contentDetailId;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Integer categoryId;

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
