package com.kotlin.board.controller.post

import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.service.post.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService,
) {

    @PostMapping()
    fun save(@RequestBody request: PostCreateRequest) {
        postService.save(request)
    }
}