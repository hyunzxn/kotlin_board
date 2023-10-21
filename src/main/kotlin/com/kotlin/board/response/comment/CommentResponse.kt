package com.kotlin.board.response.comment

import com.kotlin.board.domain.comment.Comment

data class CommentResponse(
    val id: Long,
    val writer: String,
    val content: String,
    var reComments: MutableList<CommentResponse>? = null,
) {

    companion object {
        fun of(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id!!,
                writer = comment.user.loginId,
                content = comment.content
            )
        }
    }
}
