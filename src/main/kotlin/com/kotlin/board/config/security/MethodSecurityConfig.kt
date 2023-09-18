package com.kotlin.board.config.security

import com.kotlin.board.repository.comment.CommentRepository
import com.kotlin.board.repository.post.PostRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@Configuration
@EnableMethodSecurity
class MethodSecurityConfig(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {

    @Bean
    fun methodSecurityHandler(): MethodSecurityExpressionHandler {
        val handler = DefaultMethodSecurityExpressionHandler()
        handler.setPermissionEvaluator(CustomPermissionEvaluator(postRepository, commentRepository))
        return handler
    }
}