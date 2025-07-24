package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.NoteTagMapper;
import com.example.demo.mapper.TagMapper;
import com.example.demo.model.dataobject.NoteTag;
import com.example.demo.model.dataobject.Tag;
import com.example.demo.model.dto.AddTagDTO;
import com.example.demo.model.dto.GetTagByIdDTO;
import com.example.demo.model.dto.UpdateTagDTO;
import com.example.demo.model.vo.TagListItemVO;
import com.example.demo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标签Service实现类
 */
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;


    @Override
    public Result<List<TagListItemVO>> tagList() {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagListItemVO> list = tags.stream().map(tag -> TagListItemVO.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .updateTime(tag.getUpdateTime())
                        .createTime(tag.getCreateTime())
                        .build())
                .toList();
        return Result.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> add(AddTagDTO addTagDTO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(addTagDTO,tag);

        tagMapper.insert(tag);

        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> update(UpdateTagDTO updateTagDTO) {
        Integer id = updateTagDTO.getId();
        Tag tag = tagMapper.selectById(id);

        if (Objects.isNull(tag)){
            throw new BizException(ResponseCodeEnum.TAG_NOT_FOUND);
        }

        BeanUtils.copyProperties(updateTagDTO,tag);

        tagMapper.updateById(tag);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(GetTagByIdDTO getTagByIdDTO) {
        Integer id = getTagByIdDTO.getId();
        Tag tag = tagMapper.selectById(id);

        if (Objects.isNull(tag)){
            throw new BizException(ResponseCodeEnum.TAG_NOT_FOUND);
        }

        return tagMapper.deleteById(getTagByIdDTO.getId())>0;
    }
}
