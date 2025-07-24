package com.example.demo.controller;

import com.example.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    
    /**
     * 分页查询文章
     */
    @GetMapping("/page")

    
    /**
     * 根据ID获取文章详情
     */
    @GetMapping("/detail")

    
    /**
     * 添加文章
     */
    @PostMapping("/add")

    
    /**
     * 更新文章
     */
    @PutMapping("/update")

    
    /**
     * 删除文章
     */
    @DeleteMapping("/delete")

}
