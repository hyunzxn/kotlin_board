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
        val comment: Comment = if (request.parentId != null) {
            val parentComment = commentRepository.findByIdOrThrow(request.parentId, "존재하지 않는 댓글입니다.")
            Comment.create(request.content, user, parentComment)
        } else {
            Comment.create(request.content, user)
        }
        post.addComment(comment)
    }

    fun getComments(postId: Long, pagingUtil: PagingUtil): List<CommentResponse> {
        val comments = commentRepository.findCommentsByPostId(postId, pagingUtil)
        return convertCommentsNestedStructure(comments)
    }

    @Transactional
    fun delete(id: Long) {
        val comment = commentRepository.findByIdOrThrow(id, "존재하지 않는 댓글입니다.")
        commentRepository.delete(comment)
    }

    private fun convertCommentsNestedStructure(comments: List<Comment>): List<CommentResponse> {
        val result = mutableListOf<CommentResponse>()
        val map = mutableMapOf<Long, CommentResponse>()

        comments.forEach { comment ->
            val response = CommentResponse.of(comment)
            map[response.id] = response

            if (comment.parent == null) {
                result.add(response)
            } else {
                val parentResponse = map[comment.parent!!.id]
                parentResponse?.reComments = parentResponse?.reComments ?: mutableListOf()
                parentResponse?.reComments?.add(response)
            }
        }
        return result
    }
}