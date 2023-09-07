package com.kotlin.board.repository.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.QPost.post
import com.kotlin.board.util.PagingUtil
import com.querydsl.jpa.impl.JPAQueryFactory

class PostRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : PostRepositoryCustom {

    override fun getListWithPaging(pagingUtil: PagingUtil): List<Post> {
        return queryFactory
            .select(post)
            .from(post)
            .limit(pagingUtil.size.toLong())
            .offset(pagingUtil.getOffset())
            .fetch()
    }
}