package com.kotlin.board.response.comment

import com.kotlin.board.domain.comment.Comment

data class CommentResponse(
    val id: Long,
    val content: String,
) {

    companion object {
        fun of(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id!!,
                content = comment.content
            )
        }
    }
}
