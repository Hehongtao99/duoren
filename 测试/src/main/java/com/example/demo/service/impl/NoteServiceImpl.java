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
import com.example.demo.model.dto.AddNoteDTO;
import com.example.demo.model.dto.GetNoteByIdDTO;
import com.example.demo.model.dto.NotePageQueryDTO;
import com.example.demo.model.dto.UpdateNoteDTo;
import com.example.demo.model.vo.NoteDetailVO;
import com.example.demo.model.vo.NoteListItemVO;
import com.example.demo.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private NoteDetailMapper noteDetailMapper;

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

    @Override
    public Result<NoteDetailVO> noteDetail(GetNoteByIdDTO getNoteByIdDTO) {
        Note note = noteMapper.selectById(getNoteByIdDTO.getId());
        if (Objects.isNull(note)) {
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }

        // 2. 查询详情表
        NoteDetail noteDetail = noteDetailMapper.selectById(note.getContentDetailId());
        if (Objects.isNull(noteDetail)) {
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_EMPTY);
        }

        User userName = userMapper.selectById(note.getUserId());

        Category category = categoryMapper.selectById(note.getCategoryId());

        // 1. 查询笔记关联的所有标签ID
        List<NoteTag> noteTags = noteTagMapper.selectList(
                new LambdaQueryWrapper<NoteTag>()
                        .eq(NoteTag::getNoteId, note.getId())
        );

// 2. 获取所有标签ID并去重
        List<Integer> tagIds = noteTags.stream()
                .map(NoteTag::getTagId)
                .distinct()
                .toList();

// 3. 批量查询标签对象
        List<Tag> tags = tagMapper.selectBatchIds(tagIds);

// 4. 提取标签名称集合
        List<String> tagNames = tags.stream()
                .map(Tag::getTagName)
                .toList();

        // 3. 构建VO（使用查询到的noteDetail1的时间字段）
        NoteDetailVO vo = NoteDetailVO.builder()
                .noteContent(noteDetail.getNoteContent())
                .userName(userName.getUserName())
                .tagNames(tagNames)
                .categoryName(category.getCategoryName())
                .createTime(noteDetail.getCreateTime())
                .updateTime(noteDetail.getUpdateTime())
                .build();

        return Result.success(vo);  // 返回单个对象
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 保持事务
    public Result<?> add(AddNoteDTO addNoteDTO) {
        // 1. 根据分类名查询分类
        Category category = categoryMapper.selectOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryName, addNoteDTO.getCategoryName().trim())
        );

        // 2. 插入详情表
        NoteDetail detail = NoteDetail.builder()
                .noteContent(addNoteDTO.getNoteContent())
                .build();
        noteDetailMapper.insert(detail);

        // 3. 插入主表
        Note note = Note.builder()
                .noteName(addNoteDTO.getNoteName())
                .categoryId(category.getCategoryId())
                .contentDetailId(detail.getId())
                .build();
        noteMapper.insert(note);

        // 4. 处理标签（逐条插入）
        addNoteDTO.getTagNames().forEach(tagName -> {
            Tag tag = tagMapper.selectOne(
                    new LambdaQueryWrapper<Tag>()
                            .eq(Tag::getTagName, tagName.trim())
            );
            noteTagMapper.insert(
                    NoteTag.builder()
                            .noteId(note.getId())
                            .tagId(tag.getId())
                            .build()
            );
        });

        return Result.success("添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> update(UpdateNoteDTo updateNoteDTo) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> delete(GetNoteByIdDTO getNoteByIdDTO) {

        Note note = noteMapper.selectById(getNoteByIdDTO.getId());

        if(Objects.isNull(note)){
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }

        noteMapper.deleteById(getNoteByIdDTO.getId());

        noteDetailMapper.deleteById(note.getContentDetailId());

        noteTagMapper.delete(
                new LambdaQueryWrapper<NoteTag>()
                        .eq(NoteTag::getNoteId, getNoteByIdDTO.getId())
        );

        return Result.success("删除成功");
    }

}
