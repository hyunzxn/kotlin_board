package com.kotlin.board.service.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.request.post.PostCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {

    @Transactional
    fun save(request: PostCreateRequest) {
        val post = Post.create(request.title, request.content, request.type)
        postRepository.save(post)
    }
}