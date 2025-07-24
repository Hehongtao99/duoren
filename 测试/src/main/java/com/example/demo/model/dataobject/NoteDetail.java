package com.example.demo.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 笔记详情表 DO对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("note_detail")
public class NoteDetail {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记内容
     */
    @TableField("note_content")
    private String noteContent;

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
