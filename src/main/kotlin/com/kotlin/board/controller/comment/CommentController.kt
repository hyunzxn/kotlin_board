package com.kotlin.board.controller.comment

import com.kotlin.board.request.comment.CommentCreateRequest
import com.kotlin.board.response.comment.CommentResponse
import com.kotlin.board.service.comment.CommentService
import com.kotlin.board.util.PagingUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun save(@RequestBody request: CommentCreateRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(commentService.save(request))
    }

    @GetMapping
    fun getList(pagingUtil: PagingUtil): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity.ok().body(commentService.getList(pagingUtil))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(commentService.delete(id))
    }
}