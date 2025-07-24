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
import com.example.demo.model.dto.GetCategoryByIdDTO;
import com.example.demo.model.dto.GetNoteByIdDTO;
import com.example.demo.model.vo.NoteListItemVO;
import com.example.demo.service.NoteService;
import com.example.demo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章Service实现类
 */
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Autowired
    private  NoteMapper noteMapper;

    @Override
    public Result<List<NoteListItemVO>> NotelistItem() {
        //todo

        List<Note> notes = noteMapper.selectList(null);
        List<NoteListItemVO> list = notes.stream().map(note -> NoteListItemVO.builder()
                        .id(note.getId())
                        .noteName(note.getNoteName())
                        .userId(note.getUserId())
                        .contentDetailId(note.getContentDetailId())
                        .categoryId(note.getCategoryId())
                        .createTime(note.getCreateTime())
                        .updateTime(note.getUpdateTime())
                        .build())
                .toList();
        return Result.success(list);
    }
}
