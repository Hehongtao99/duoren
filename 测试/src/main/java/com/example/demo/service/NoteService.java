package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.model.dataobject.Note;
import com.example.demo.model.dto.AddNoteDTO;
import com.example.demo.model.dto.GetNoteByIdDTO;
import com.example.demo.model.dto.NotePageQueryDTO;
import com.example.demo.model.dto.UpdateNoteDTo;
import com.example.demo.model.vo.NoteDetailVO;
import com.example.demo.model.vo.NoteListItemVO;

import java.util.List;

/**
 * 文章Service接口
 */
public interface NoteService extends IService<Note> {
    Result<List<NoteListItemVO>> NotelistItem();

    PageResult<NoteListItemVO> getNoteByPage(NotePageQueryDTO notePageQueryDTO);

    Result<NoteDetailVO> noteDetail(GetNoteByIdDTO getNoteByIdDTO);

    Result<?> add(AddNoteDTO addNoteDTO);

    Result<?> update(UpdateNoteDTo updateNoteDTo);

    Result<?> delete(GetNoteByIdDTO getNoteByIdDTO);

}
