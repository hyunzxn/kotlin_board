package com.kotlin.board.service.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.postlike.PostLike
import com.kotlin.board.domain.user.User
import com.kotlin.board.repository.post.postlike.PostLikeRepository
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
    private val postLikeRepository: PostLikeRepository,
) {

    @Transactional
    fun save(request: PostCreateRequest, userId: Long) {
        val user = findUserByIdOrThrowException(userId)
        val post = Post.create(request.title, request.content, request.type, user)
        postRepository.save(post)
    }

    @Transactional
    fun postLike(userId: Long, postId: Long) {
        val user = findUserByIdOrThrowException(userId)
        val post = findPostByIdOrThrowException(postId)
        val postLike = postLikeRepository.findByUserIdAndPostId(userId, postId)

        postLike?.run {
            postLikeRepository.delete(this)
            postLikeRepository.updateLikeCount(post, true)
        } ?: run {
            val newPostLike = PostLike.create(user, post)
            postLikeRepository.save(newPostLike)
            postLikeRepository.updateLikeCount(post, false)
        }
    }

    fun getListWithPaging(pagingUtil: PagingUtil): List<PostResponse> {
        return postRepository.getListWithPaging(pagingUtil)
            .map { post -> PostResponse.of(post) }
    }

    fun getOne(id: Long): PostResponse {
        val post = findPostByIdOrThrowException(id)
        return PostResponse.of(post)
    }

    fun getBySearchKeyword(keyword: String): List<PostResponse> {
        return postRepository.getBySearchKeyword(keyword)
            .map { post -> PostResponse.of(post) }
    }

    @Transactional
    fun update(id: Long, request: PostUpdateRequest): PostResponse {
        val post = findPostByIdOrThrowException(id)
        post.update(request)
        return PostResponse.of(post)
    }

    @Transactional
    fun delete(id: Long) {
        val post = findPostByIdOrThrowException(id)
        postRepository.delete(post)
    }

    private fun findUserByIdOrThrowException(id: Long, message: String = "존재하지 않는 유저입니다. 요청한 유저 id=$id"): User {
        return userRepository.findByIdOrThrow(id, message)
    }

    private fun findPostByIdOrThrowException(id: Long, message: String = "존재하지 않는 게시글입니다. 요청한 게시글 id=$id"): Post {
        return postRepository.findByIdOrThrow(id, message)
    }
}