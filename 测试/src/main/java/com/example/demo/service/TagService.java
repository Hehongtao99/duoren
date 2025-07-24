package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Result;
import com.example.demo.model.dataobject.Tag;
import com.example.demo.model.dto.AddTagDTO;
import com.example.demo.model.dto.GetTagByIdDTO;
import com.example.demo.model.dto.UpdateTagDTO;
import com.example.demo.model.vo.TagListItemVO;

import java.util.List;

/**
 * 标签Service接口
 */
public interface TagService extends IService<Tag> {
    Result<List<TagListItemVO>> tagList();

    Result<?> add(AddTagDTO addTagDTO);

    Result<?> update(UpdateTagDTO updateTagDTO);

    boolean delete(GetTagByIdDTO getTagByIdDTO);

}
