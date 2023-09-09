package com.kotlin.board.util

import org.springframework.data.domain.Sort
import kotlin.math.max
import kotlin.math.min

class PagingUtil(
    private val page: Int = 1,
    val size: Int = 10,
    var sort: Sort.Direction = Sort.Direction.DESC
) {

    fun getOffset(): Long {
        return (max(1, page) - 1) * min(size, MAX_SIZE).toLong()
    }

    companion object {
        private const val MAX_SIZE = 2000
    }
}