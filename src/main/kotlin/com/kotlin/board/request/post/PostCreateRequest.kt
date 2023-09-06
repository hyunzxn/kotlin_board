package com.kotlin.board.request.post

import com.kotlin.board.domain.post.PostType

data class PostCreateRequest(
    val title: String,
    val content: String,
    val type: PostType,
)
