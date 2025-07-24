package com.example.demo.model.dto;

import com.example.demo.common.PageParam;
import com.example.demo.model.dataobject.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class NotePageQueryDTO extends PageParam {


    /**
     * 笔记名称
     */

    private String noteName;

    private Integer categoryId;


}
