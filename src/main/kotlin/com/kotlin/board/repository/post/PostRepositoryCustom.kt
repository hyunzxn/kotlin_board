package com.kotlin.board.repository.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.util.PagingUtil

interface PostRepositoryCustom {

    fun getListWithPaging(pagingUtil: PagingUtil): List<Post>

    fun getBySearchKeyword(keyword: String): List<Post>
}