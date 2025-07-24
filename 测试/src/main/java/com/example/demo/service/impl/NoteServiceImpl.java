package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.*;
import com.example.demo.model.dataobject.*;
import com.example.demo.model.dto.NotePageQueryDTO;
import com.example.demo.model.vo.NoteListItemVO;
import com.example.demo.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文章Service实现类
 */
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Autowired
    private  NoteMapper noteMapper;

    @Autowired
    private  CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<List<NoteListItemVO>> NotelistItem() {

//        List<Note> notes = noteMapper.selectList(null);
//        return Result.success(notes);
        return null;
    }

    @Override
    public PageResult<NoteListItemVO> getNoteByPage(NotePageQueryDTO notePageQueryDTO) {
        Integer current = notePageQueryDTO.getCurrent();
        Integer size = notePageQueryDTO.getSize();
        String noteName = notePageQueryDTO.getNoteName();
        Integer categoryId = notePageQueryDTO.getCategoryId();

        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();

        if(categoryId!=null){
            wrapper.eq(Note::getCategoryId,categoryId);
        }

        wrapper.like(StrUtil.isNotBlank(noteName),Note::getNoteName,noteName)
                .orderByDesc(Note::getCreateTime);

        Page<Note> notePage = new Page<>(current, size);

        Page<Note> notePage1 = noteMapper.selectPage(notePage, wrapper);
        if(Objects.isNull(notePage1)){
            throw new BizException(ResponseCodeEnum.PAGE_NOT_FOUND);
        }


        Map<Integer,String> userMap = new HashMap<>();
        List<Integer> Ids = notePage1.getRecords().stream().map(Note::getUserId).toList();
        Ids.forEach(Id -> {
            User user = userMapper.selectById(Id);
            if(Objects.isNull(user)){
                throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }
            userMap.put(Id,user.getUserName());
            });


        Map<Integer,String> categoryMap = new HashMap<>();
        List<Integer> categoryIds = notePage1.getRecords().stream().map(Note::getCategoryId).toList();
        categoryIds.forEach(categoryId1 -> {
            Category category = categoryMapper.selectById(categoryId1);
            if(Objects.isNull(category)){
                throw new BizException(ResponseCodeEnum.CATEGORY_NOT_FOUND);
            }
            categoryMap.put(categoryId1,category.getCategoryName());
        });

        List<NoteListItemVO> collect = notePage1.getRecords().stream().map(note -> NoteListItemVO.builder()
                        .id(note.getId())
                        .noteName(note.getNoteName())
                        .userName(userMap.get(note.getUserId()))
                        .categoryName(categoryMap.get(note.getCategoryId()))
                        .createTime(note.getCreateTime())
                        .build())
                .toList();

        return PageResult.success(notePage1, collect);
    }
}
