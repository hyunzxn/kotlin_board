package com.kotlin.board.repository.post.postlike

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.QPost.post
import com.querydsl.jpa.impl.JPAQueryFactory

class PostLikeRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : PostLikeRepositoryCustom {

    override fun updateLikeCount(postToBeUpdate: Post, isAlreadyLiked: Boolean) {
        if (isAlreadyLiked) {
            queryFactory
                .update(post)
                .set(post.likeCount, post.likeCount.subtract(1))
                .where(post.eq(postToBeUpdate))
                .execute()
        } else {
            queryFactory
                .update(post)
                .set(post.likeCount, post.likeCount.add(1))
                .where(post.eq(postToBeUpdate))
                .execute()
        }
    }

}