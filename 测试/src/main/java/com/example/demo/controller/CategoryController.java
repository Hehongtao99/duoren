package com.example.demo.controller;

import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类Controller
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 获取所有分类
     */
    @GetMapping("/list")

    
    /**
     * 添加分类
     */
    @PostMapping("/add")

    
    /**
     * 更新分类
     */
    @PutMapping("/update")

    
    /**
     * 删除分类
     */
    @DeleteMapping("/delete")

}
