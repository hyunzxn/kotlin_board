package com.kotlin.board.repository.comment

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.util.PagingUtil

interface CommentRepositoryCustom {

    fun findCommentsByPostId(postId: Long, pagingUtil: PagingUtil): List<Comment>
}