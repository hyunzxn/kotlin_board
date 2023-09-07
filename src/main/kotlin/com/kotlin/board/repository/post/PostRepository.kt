package com.kotlin.board.repository.post

import com.kotlin.board.domain.post.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>, PostRepositoryCustom