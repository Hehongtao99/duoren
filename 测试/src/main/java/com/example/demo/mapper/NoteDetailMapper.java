package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.dataobject.NoteDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章详情Mapper接口
 */
@Mapper
public interface NoteDetailMapper extends BaseMapper<NoteDetail> {
}
