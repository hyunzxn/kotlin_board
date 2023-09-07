package com.kotlin.board.service.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.request.post.PostUpdateRequest
import com.kotlin.board.response.post.PostResponse
import com.kotlin.board.util.findByIdOrThrow
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

    fun getList(): List<PostResponse> {
        return postRepository.findAll()
            .map { post -> PostResponse.of(post) }
    }

    fun getOne(id: Long): PostResponse {
        val post = postRepository.findByIdOrThrow(id, "존재하지 않는 게시글입니다.")
        return PostResponse.of(post)
    }

    @Transactional
    fun update(id: Long, request: PostUpdateRequest): PostResponse {
        val post = postRepository.findByIdOrThrow(id, "존재하지 않는 게시글입니다.")
        post.update(request)
        return PostResponse.of(post)
    }

    @Transactional
    fun delete(id: Long) {
        val post = postRepository.findByIdOrThrow(id, "존재하지 않는 게시글입니다.")
        postRepository.delete(post)
    }
}