package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.model.dto.AddCategroyDTO;
import com.example.demo.model.dto.AddNoteDTO;
import com.example.demo.model.dto.GetNoteByIdDTO;
import com.example.demo.model.dto.NotePageQueryDTO;
import com.example.demo.model.vo.NoteDetailVO;
import com.example.demo.model.vo.NoteListItemVO;
import com.example.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章Controller
 */
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    
    @Autowired
    private NoteService noteService;

    /**
     * 获取文章列表
     */
    @GetMapping("/list")
    public Result<List<NoteListItemVO>> listResult(){
        return noteService.NotelistItem();
    }

    /**
     * 分页查询文章
     */
    @GetMapping("/page")
    public PageResult<NoteListItemVO> pageQuery(NotePageQueryDTO dto){
        return noteService.getNoteByPage(dto);
    }


    /**
     * 根据ID获取文章详情
     */
    @GetMapping("/detail")
    public Result<NoteDetailVO> detail(GetNoteByIdDTO getNoteByIdDTO){
        return noteService.noteDetail(getNoteByIdDTO);
    }


    /**
     * 添加文章
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody AddNoteDTO addNoteDTO){
        return noteService.add(addNoteDTO);
    }


//    /**
//     * 更新文章
//     */
//    @PutMapping("/update")
//
//
    /**
     * 删除文章
     */
    @DeleteMapping("/delete")
    public Result<?> delete(GetNoteByIdDTO getNoteByIdDTO){
        return noteService.delete(getNoteByIdDTO);
    }

}
