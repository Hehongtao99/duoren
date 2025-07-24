package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.dataobject.NoteTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联Mapper接口
 */
@Mapper
public interface NoteTagMapper extends BaseMapper<NoteTag> {
}
