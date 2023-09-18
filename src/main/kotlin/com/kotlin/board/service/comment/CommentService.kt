package com.kotlin.board.service.comment

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.repository.comment.CommentRepository
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.request.comment.CommentCreateRequest
import com.kotlin.board.response.comment.CommentResponse
import com.kotlin.board.util.PagingUtil
import com.kotlin.board.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun save(request: CommentCreateRequest, userId: Long) {
        val user = userRepository.findByIdOrThrow(userId, "존재하지 않는 유저입니다.")
        val post = postRepository.findByIdOrThrow(request.postId, "존재하지 않는 게시글입니다.")
        val comment = Comment.create(request.content, post, user)
        commentRepository.save(comment)
    }

    fun getList(pagingUtil: PagingUtil): List<CommentResponse> {
        return commentRepository.getListWithPaging(pagingUtil)
            .map { comment -> CommentResponse.of(comment) }
    }

    @Transactional
    fun delete(id: Long) {
        val comment = commentRepository.findByIdOrThrow(id, "존재하지 않는 댓글입니다.")
        commentRepository.delete(comment)
    }
}