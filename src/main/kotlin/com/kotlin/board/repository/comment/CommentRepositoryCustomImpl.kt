package com.kotlin.board.repository.comment

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.domain.comment.QComment.comment
import com.kotlin.board.util.PagingUtil
import com.querydsl.jpa.impl.JPAQueryFactory
import java.util.Collections
import java.util.stream.Collectors

class CommentRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : CommentRepositoryCustom {

    override fun findCommentsByPostId(postId: Long, pagingUtil: PagingUtil): List<Comment> {
        return queryFactory
            .selectFrom(comment)
            .leftJoin(comment.parent).fetchJoin().distinct()
            .limit(pagingUtil.size.toLong())
            .offset(pagingUtil.getOffset())
            .where(comment.post.id.eq(postId))
            .orderBy(
                comment.parent.id.asc().nullsFirst(),
                comment.createdAt.asc()
            )
            .fetch()
    }
}