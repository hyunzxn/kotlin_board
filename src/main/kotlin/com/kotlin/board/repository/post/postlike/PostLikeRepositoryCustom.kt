package com.kotlin.board.repository.post.postlike

import com.kotlin.board.domain.post.Post

interface PostLikeRepositoryCustom {

    fun updateLikeCount(postToBeUpdate: Post, isAlreadyLiked: Boolean)
}