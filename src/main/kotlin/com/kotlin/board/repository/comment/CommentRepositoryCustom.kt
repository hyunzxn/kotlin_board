package com.kotlin.board.repository.comment

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.util.PagingUtil

interface CommentRepositoryCustom {

    fun getListWithPaging(pagingUtil: PagingUtil): List<Comment>
}