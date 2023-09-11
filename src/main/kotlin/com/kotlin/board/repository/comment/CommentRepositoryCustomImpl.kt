package com.kotlin.board.repository.comment

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.domain.comment.QComment.comment
import com.kotlin.board.util.PagingUtil
import com.querydsl.jpa.impl.JPAQueryFactory

class CommentRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : CommentRepositoryCustom {

    override fun getListWithPaging(pagingUtil: PagingUtil): List<Comment> {
        return queryFactory
            .select(comment)
            .from(comment)
            .limit(pagingUtil.size.toLong())
            .offset(pagingUtil.getOffset())
            .orderBy(comment.id.desc())
            .fetch()
    }
}