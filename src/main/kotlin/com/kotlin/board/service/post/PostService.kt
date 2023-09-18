package com.kotlin.board.service.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.request.post.PostUpdateRequest
import com.kotlin.board.response.post.PostResponse
import com.kotlin.board.util.PagingUtil
import com.kotlin.board.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun save(request: PostCreateRequest, userId: Long) {
        val user = userRepository.findByIdOrThrow(userId, "존재하지 않는 유저입니다.") //todo 예외 메시지 수정 필요
        val post = Post.create(request.title, request.content, request.type, user)
        postRepository.save(post)
    }

    fun getListWithPaging(pagingUtil: PagingUtil): List<PostResponse> {
        return postRepository.getListWithPaging(pagingUtil)
            .map { post -> PostResponse.of(post) }
    }

    fun getOne(id: Long): PostResponse {
        val post = findByIdOrThrowException(id)
        return PostResponse.of(post)
    }

    fun getBySearchKeyword(keyword: String): List<PostResponse> {
        return postRepository.getBySearchKeyword(keyword)
            .map { post -> PostResponse.of(post) }
    }

    @Transactional
    fun update(id: Long, request: PostUpdateRequest): PostResponse {
        val post = findByIdOrThrowException(id)
        post.update(request)
        return PostResponse.of(post)
    }

    @Transactional
    fun delete(id: Long) {
        val post = findByIdOrThrowException(id)
        postRepository.delete(post)
    }

    private fun findByIdOrThrowException(id: Long, message: String = "존재하지 않는 게시글입니다."): Post {
        return postRepository.findByIdOrThrow(id, message)
    }
}