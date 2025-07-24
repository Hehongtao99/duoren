package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.dto.AddCategroyDTO;
import com.example.demo.model.dto.GetCategoryByIdDTO;
import com.example.demo.model.dto.UpdateCategroyDTO;
import com.example.demo.model.vo.GetCategoryByIdVO;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<List<GetCategoryByIdVO>> getCategoryByIdVO(){
        return categoryService.getCategoryById();
    }


    /**
     * 添加分类
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody AddCategroyDTO addCategroyDTO){
        return categoryService.add(addCategroyDTO);
    }


    /**
     * 更新分类
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody UpdateCategroyDTO updateCategroyDTO){
        return categoryService.update(updateCategroyDTO);
    }




    /**
     * 删除分类
     */
    @DeleteMapping("/delete")
    public boolean delete(GetCategoryByIdDTO getCategoryByIdDTO){
        return categoryService.delete(getCategoryByIdDTO);
    }

}
