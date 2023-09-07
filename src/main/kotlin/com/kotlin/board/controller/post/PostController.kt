package com.kotlin.board.controller.post

import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.request.post.PostUpdateRequest
import com.kotlin.board.response.post.PostResponse
import com.kotlin.board.service.post.PostService
import com.kotlin.board.util.PagingUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService,
) {

    @PostMapping()
    fun save(@RequestBody request: PostCreateRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(postService.save(request))
    }

    @GetMapping
    fun getListWithPaging(pagingUtil: PagingUtil): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok().body(postService.getListWithPaging(pagingUtil))
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<PostResponse> {
        return ResponseEntity.ok().body(postService.getOne(id))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: PostUpdateRequest): ResponseEntity<PostResponse> {
        return ResponseEntity.ok().body(postService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(postService.delete(id))
    }
}