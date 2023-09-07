package com.kotlin.board.request.post

import com.kotlin.board.domain.post.PostType

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val type: PostType,
)