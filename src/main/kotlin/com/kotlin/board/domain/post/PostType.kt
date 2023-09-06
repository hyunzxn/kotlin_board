package com.kotlin.board.domain.post

enum class PostType(
    private val text: String,
) {

    FREE("자유"),
    ASK("질문"),
    NOTICE("공지"),

}