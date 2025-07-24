package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.mapper.*;
import com.example.demo.model.dataobject.*;
import com.example.demo.model.dto.NotePageQueryDTO;
import com.example.demo.model.vo.NoteListItemVO;
import com.example.demo.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageResult<NoteListItemVO> getNoteByPage(NotePageQueryDTO notePageQueryDTO) {
        Integer current = notePageQueryDTO.getCurrent();
        Integer size = notePageQueryDTO.getSize();

        return null;
    }
}
