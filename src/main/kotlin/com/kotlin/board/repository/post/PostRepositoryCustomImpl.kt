package com.kotlin.board.repository.post

import com.kotlin.board.domain.comment.QComment
import com.kotlin.board.domain.comment.QComment.*
import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.QPost.post
import com.kotlin.board.domain.user.QUser
import com.kotlin.board.domain.user.QUser.*
import com.kotlin.board.util.PagingUtil
import com.querydsl.jpa.impl.JPAQueryFactory

class PostRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : PostRepositoryCustom {

    override fun getListWithPaging(pagingUtil: PagingUtil): List<Post> {
        return queryFactory
            .select(post)
            .distinct()
            .from(post)
            .leftJoin(post.comments).fetchJoin()
            .limit(pagingUtil.size.toLong())
            .offset(pagingUtil.getOffset())
            .orderBy(post.id.desc())
            .fetch()
    }

    override fun getBySearchKeyword(keyword: String): List<Post> {
        return queryFactory
            .select(post)
            .from(post)
            .where(post.title.containsIgnoreCase(keyword))
            .leftJoin(post.user).fetchJoin()
            .leftJoin(post.comments).fetchJoin()
            .fetch()
    }
}