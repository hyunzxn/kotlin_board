package com.kotlin.board.controller.comment

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.request.comment.CommentCreateRequest
import com.kotlin.board.response.comment.CommentResponse
import com.kotlin.board.service.comment.CommentService
import com.kotlin.board.util.PagingUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun save(@RequestBody request: CommentCreateRequest): ResponseEntity<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity.ok().body(commentService.save(request, userId))
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/{parentId}")
    fun saveReComment(@RequestBody request: CommentCreateRequest, @PathVariable parentId: Long): ResponseEntity<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity.ok().body(commentService.saveReComment(parentId, userId, request))
    }

    @GetMapping
    fun getList(pagingUtil: PagingUtil): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity.ok().body(commentService.getList(pagingUtil))
    }

    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'Comment', 'DELETE')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(commentService.delete(id))
    }
}