package com.kotlin.board.response.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.PostType

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val type: PostType,
) {

    companion object {
        fun of(post: Post): PostResponse {
            return PostResponse(
                id = post.id!!,
                title = post.title,
                content = post.content,
                type = post.type
            )
        }
    }
}
