package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.dataobject.Note;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章Mapper接口
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
}
