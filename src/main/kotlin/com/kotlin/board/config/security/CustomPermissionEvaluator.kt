package com.kotlin.board.config.security

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.domain.post.Post
import com.kotlin.board.repository.comment.CommentRepository
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.util.findByIdOrThrow
import com.kotlin.board.util.logger
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import java.io.Serializable

class CustomPermissionEvaluator(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : PermissionEvaluator {

    private val log = logger()

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        if (targetDomainObject is Post) {
            return hasPostPermission(authentication, targetDomainObject)
        } else if (targetDomainObject is Comment) {
            return hasCommentPermission(authentication, targetDomainObject)
        }
        return false
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        if (targetType == "Post" && targetId is Long) {
            val post = postRepository.findByIdOrThrow(targetId, "존재하지 않는 게시글입니다.")
            return hasPostPermission(authentication, post)
        } else if (targetType == "Comment" && targetId is Long) {
            val comment = commentRepository.findByIdOrThrow(targetId, "존재하지 않는 댓글입니다.")
            return hasCommentPermission(authentication, comment)
        }
        return false
    }

    private fun hasPostPermission(authentication: Authentication?, post: Post): Boolean {
        val userPrincipal: CustomUser = authentication?.principal as CustomUser

        if (!post.user.id?.equals(userPrincipal.userId)!!) {
            log.error("[인가실패] 해당 사용자가 작성한 글이 아닙니다. postId={}", post.id)
            return false
        }

        return true
    }

    private fun hasCommentPermission(authentication: Authentication?, comment: Comment): Boolean {
        val userPrincipal: CustomUser = authentication?.principal as CustomUser

        if (!comment.user.id?.equals(userPrincipal.userId)!!) {
            log.error("[인가실패] 해당 사용자가 작성한 댓글이 아닙니다. commentId={}", comment.id)
            return false
        }

        return true
    }
}