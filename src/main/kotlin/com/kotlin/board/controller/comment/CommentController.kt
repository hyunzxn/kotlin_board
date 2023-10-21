package com.kotlin.board.controller.comment

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.request.comment.CommentCreateRequest
import com.kotlin.board.response.comment.CommentResponse
import com.kotlin.board.service.comment.CommentService
import com.kotlin.board.util.PagingUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun save(@RequestBody request: CommentCreateRequest, @AuthenticationPrincipal loginUser: CustomUser): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(commentService.save(request, loginUser.userId))
    }

    @GetMapping
    fun getList(@RequestParam postId: Long, pagingUtil: PagingUtil): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity.ok().body(commentService.getComments(postId, pagingUtil))
    }

    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'Comment', 'DELETE')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(commentService.delete(id))
    }
}