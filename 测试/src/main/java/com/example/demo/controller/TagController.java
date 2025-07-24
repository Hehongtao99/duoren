package com.example.demo.controller;

import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签Controller
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    
    @Autowired
    private TagService tagService;
//
//    /**
//     * 获取所有标签
//     */
//    @GetMapping("/list")
//
//
//    /**
//     * 添加标签
//     */
//    @PostMapping("/add")
//
//
//    /**
//     * 更新标签
//     */
//    @PutMapping("/update")
//
//
//    /**
//     * 删除标签
//     */
//    @DeleteMapping("/delete")

}
