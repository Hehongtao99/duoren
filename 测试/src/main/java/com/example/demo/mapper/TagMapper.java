package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.dataobject.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签Mapper接口
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
