package com.kotlin.board.response.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.PostType
import com.kotlin.board.response.comment.CommentResponse

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val type: PostType,
    val comments: List<CommentResponse>,
) {

    companion object {
        fun of(post: Post): PostResponse {
            return PostResponse(
                id = post.id!!,
                title = post.title,
                content = post.content,
                type = post.type,
                comments = post.comments.map { comment -> CommentResponse.of(comment) }
            )
        }
    }
}
