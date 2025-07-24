package com.example.demo.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 笔记标签关联表 DO对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("note_tag")
public class NoteTag {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记ID
     */
    @TableField("note_id")
    private Integer noteId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private Integer tagId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
