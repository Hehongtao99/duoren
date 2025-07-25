package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.*;
import com.example.demo.model.dataobject.*;
import com.example.demo.model.dto.NotePageQueryDTO;
import com.example.demo.model.vo.NoteListItemVO;
import com.example.demo.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 文章Service实现类
 */
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Autowired
    private  NoteMapper noteMapper;

    @Autowired
    private  CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoteTagMapper noteTagMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result<List<NoteListItemVO>> NotelistItem() {

//        List<Note> notes = noteMapper.selectList(null);
//        return Result.success(notes);
        return null;
    }

    @Override
    public PageResult<NoteListItemVO> getNoteByPage(NotePageQueryDTO notePageQueryDTO) {
        Integer current = notePageQueryDTO.getCurrent();
        Integer size = notePageQueryDTO.getSize();
        String noteName = notePageQueryDTO.getNoteName();
        Integer categoryId = notePageQueryDTO.getCategoryId();

        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();

        if(categoryId!=null){
            wrapper.eq(Note::getCategoryId,categoryId);
        }

        wrapper.like(StrUtil.isNotBlank(noteName),Note::getNoteName,noteName)
                .orderByDesc(Note::getCreateTime);

        Page<Note> notePage = new Page<>(current, size);

        Page<Note> notePage1 = noteMapper.selectPage(notePage, wrapper);
        if(Objects.isNull(notePage1)){
            throw new BizException(ResponseCodeEnum.PAGE_NOT_FOUND);
        }

        //获取所有笔记id
        List<Integer> noteIds = notePage1.getRecords().stream().map(Note::getId).toList();

        //批量查询标签关联关系,查询所有noteTag的条目
        LambdaQueryWrapper<NoteTag> noteTagLambdaQueryWrapper=new LambdaQueryWrapper<>();
        noteTagLambdaQueryWrapper.in(NoteTag::getNoteId,noteIds);
        List<NoteTag> noteTags = noteTagMapper.selectList(noteTagLambdaQueryWrapper);


        Map<Integer, List<Integer>> noteTagMap = noteTags.stream().collect(
                Collectors.groupingBy(
                        NoteTag::getNoteId,
                        Collectors.mapping(
                                NoteTag::getTagId,
                                Collectors.toList()
                        )
                )
        );

        //构建noteId和tagName的映射
        Map<Integer, List<String>> noteTagMap1 = new HashMap<>();
        noteIds.forEach(noteId -> {
                    List<Integer> tagIds = noteTagMap.get(noteId);
                    if(Objects.nonNull(tagIds)){
                        List<String> tagNames = tagIds.stream().map(tagId -> tagMapper.selectById(tagId).getTagName()).toList();
                        noteTagMap1.put(noteId,tagNames);
                        }
        });


//        //获取所有标签ID，去重
//        List<Integer> tagIds = noteTags.stream().map(NoteTag::getTagId).distinct().toList();
//
//        //批量查询标签信息，并将标签数据转换为tagId到tagName的映射
//        Map<Integer, String> tagMap = new HashMap<>();
//        if(!tagIds.isEmpty()){
//            LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            tagLambdaQueryWrapper.in(Tag::getId,tagIds);
////            Tag{id=5, tagName="Java", ...},
////            Tag{id=8, tagName="Spring", ...}
//            List<Tag> tags = tagMapper.selectList(tagLambdaQueryWrapper);
//            //转为map
//            tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, Tag::getTagName));
//
//        }
//
//        // 构建 noteId -> [tagName1, tagName2,...] 的映射
//        Map<Integer, List<String>> noteTagMap = new HashMap<>();
//
//        noteTags.forEach(noteTag -> {
//            // 1. 获取或创建该noteId对应的标签列表
//            List<String> tagNames = noteTagMap.computeIfAbsent(
//                    noteTag.getNoteId(),
//                    k -> new ArrayList<>()
//            );
//
//            // 2. 从tagMap中获取标签名并添加到列表
//            String tagName = tagMap.get(noteTag.getTagId());
//            if (tagName != null) {  // 防止tagMap中找不到对应的tagName
//                tagNames.add(tagName);
//            }
//        });


        Map<Integer,String> userMap = new HashMap<>();
        List<Integer> Ids = notePage1.getRecords().stream().map(Note::getUserId).toList();
        Ids.forEach(Id -> {
            User user = userMapper.selectById(Id);
            if(Objects.isNull(user)){
                throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }
            userMap.put(Id,user.getUserName());
            });


        Map<Integer,String> categoryMap = new HashMap<>();
        List<Integer> categoryIds = notePage1.getRecords().stream().map(Note::getCategoryId).toList();
        categoryIds.forEach(categoryId1 -> {
            Category category = categoryMapper.selectById(categoryId1);
            if(Objects.isNull(category)){
                throw new BizException(ResponseCodeEnum.CATEGORY_NOT_FOUND);
            }
            categoryMap.put(categoryId1,category.getCategoryName());
        });



        List<NoteListItemVO> collect = notePage1.getRecords().stream().map(note -> NoteListItemVO.builder()
                        .id(note.getId())
                        .noteName(note.getNoteName())
                        .userName(userMap.get(note.getUserId()))
                        .categoryName(categoryMap.get(note.getCategoryId()))
                        .tagNames(noteTagMap1.get(note.getId()))
                        .createTime(note.getCreateTime())
                        .build())
                .toList();

        return PageResult.success(notePage1, collect);
    }
}
