package com.kotlin.board.controller.post

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.request.post.PostUpdateRequest
import com.kotlin.board.response.post.PostResponse
import com.kotlin.board.service.post.PostService
import com.kotlin.board.util.PagingUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService,
) {

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun save(@RequestBody request: PostCreateRequest, @AuthenticationPrincipal loginUser: CustomUser): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(postService.save(request, loginUser.userId))
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/likes/{postId}")
    fun postLike(@PathVariable postId: Long, @AuthenticationPrincipal loginUser: CustomUser): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(postService.postLike(loginUser.userId, postId))
    }

    @GetMapping
    fun getListWithPaging(pagingUtil: PagingUtil): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok().body(postService.getListWithPaging(pagingUtil))
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<PostResponse> {
        return ResponseEntity.ok().body(postService.getOne(id))
    }

    @GetMapping("/search")
    fun getBySearchKeyword(@RequestParam keyword: String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok().body(postService.getBySearchKeyword(keyword))
    }

    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'Post', 'PUT')")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: PostUpdateRequest): ResponseEntity<PostResponse> {
        return ResponseEntity.ok().body(postService.update(id, request))
    }

    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'Post', 'DELETE')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(postService.delete(id))
    }
}