package com.kotlin.board.request.comment

data class CommentCreateRequest(
    val postId: Long,
    val content: String,
    val parentId: Long?,
)
