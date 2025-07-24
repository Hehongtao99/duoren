package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.model.dataobject.Note;
import com.example.demo.model.vo.NoteListItemVO;

import java.util.List;

/**
 * 文章Service接口
 */
public interface NoteService extends IService<Note> {
    Result<List<NoteListItemVO>> NotelistItem();
    

}
