package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Result;
import com.example.demo.model.dataobject.Category;
import com.example.demo.model.dto.AddCategroyDTO;
import com.example.demo.model.dto.GetCategoryByIdDTO;
import com.example.demo.model.dto.UpdateCategroyDTO;
import com.example.demo.model.vo.GetCategoryByIdVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类Service接口
 */
public interface CategoryService extends IService<Category> {
    Result<List<GetCategoryByIdVO>> getCategoryById();

    Result<?> add(AddCategroyDTO addCategroyDTO);

    Result<?> update(UpdateCategroyDTO updateCategroyDTO);

    boolean delete(GetCategoryByIdDTO getCategoryByIdDTO);
}
