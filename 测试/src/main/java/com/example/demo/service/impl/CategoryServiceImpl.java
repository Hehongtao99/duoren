package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dataobject.Category;
import com.example.demo.model.dto.AddCategroyDTO;
import com.example.demo.model.dto.GetCategoryByIdDTO;
import com.example.demo.model.dto.UpdateCategroyDTO;
import com.example.demo.model.vo.GetCategoryByIdVO;
import com.example.demo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 分类Service实现类
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public Result<List<GetCategoryByIdVO>> getCategoryById() {
        List<Category> categories = categoryMapper.selectList(null);
        List<GetCategoryByIdVO> collect = categories.stream().map(category ->
                        GetCategoryByIdVO.builder()
                                .categoryId(category.getCategoryId())
                                .categoryName(category.getCategoryName())
                                .createTime(category.getCreateTime())
                                .updateTime(category.getUpdateTime())
                                .build()).toList();

        if (collect.isEmpty()){
            throw new BizException(ResponseCodeEnum.SYSTEM_ERROR);
        }

        return Result.success(collect);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> add(AddCategroyDTO addCategroyDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategroyDTO,category);

        categoryMapper.insert(category);
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> update(UpdateCategroyDTO updateCategroyDTO) {
        Integer categoryId = updateCategroyDTO.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);

        if(Objects.isNull(category)){
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_FOUND);
        }

        BeanUtils.copyProperties(updateCategroyDTO,category);
        categoryMapper.updateById(category);

        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(GetCategoryByIdDTO getCategoryByIdDTO) {
        Integer categoryId = getCategoryByIdDTO.getCategoryId();
        categoryMapper.selectById(categoryId);

        if (Objects.isNull(categoryId)){
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_FOUND);
        }

        return categoryMapper.deleteById(getCategoryByIdDTO.getCategoryId())>0;
    }
}
