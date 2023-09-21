package com.kotlin.board.repository.post.postlike

import com.kotlin.board.domain.post.postlike.PostLike
import com.kotlin.board.repository.post.PostRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeRepository : JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {

    fun findByUserIdAndPostId(userId: Long, postId: Long): PostLike?

}