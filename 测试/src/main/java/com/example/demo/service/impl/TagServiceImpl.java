package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.NoteTagMapper;
import com.example.demo.mapper.TagMapper;
import com.example.demo.model.dataobject.NoteTag;
import com.example.demo.model.dataobject.Tag;
import com.example.demo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标签Service实现类
 */
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    

}
