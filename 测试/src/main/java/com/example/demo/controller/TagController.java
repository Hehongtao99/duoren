package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.dto.AddTagDTO;
import com.example.demo.model.dto.GetTagByIdDTO;
import com.example.demo.model.dto.UpdateTagDTO;
import com.example.demo.model.vo.TagListItemVO;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签Controller
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    
    @Autowired
    private TagService tagService;
    /**
     * 获取所有标签
     */
    @GetMapping("/list")
    public Result<List<TagListItemVO>> tagList(){
        return tagService.tagList();
    }


    /**
     * 添加标签
     */
    @PostMapping("/add")
    public Result<?> add(AddTagDTO addTagDTO){
        return tagService.add(addTagDTO);
    }


    /**
     * 更新标签
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody UpdateTagDTO updateTagDTO){
        return tagService.update(updateTagDTO);
    }


    /**
     * 删除标签
     */
    @DeleteMapping("/delete")
    public boolean delete(GetTagByIdDTO getTagByIdDTO){
        return tagService.delete(getTagByIdDTO);
    }

}
