package com.example.demo.enums;

import com.example.demo.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("KV-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("KV-10001", "参数错误"),

    // ----------- 业务异常状态码 -----------
    USER_NOT_FOUND("KV-20000", "该用户不存在"),
    //字典
    PAGE_NOT_FOUND("KV-20001", "分页数据不存在"),

    // ----------- 文章相关异常状态码 -----------
    NOTE_NOT_FOUND("KV-30000", "文章不存在"),
    CATEGORY_NOT_FOUND("KV-30001", "分类不存在"),
    TAG_NOT_FOUND("KV-30002", "标签不存在"),
    NOTE_CONTENT_EMPTY("KV-30003", "文章内容不能为空"),
    CATEGORY_NAME_EXISTS("KV-30004", "分类名称已存在"),
    TAG_NAME_EXISTS("KV-30005", "标签名称已存在"),

    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;

}