package com.kotlin.board.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID, message: String): T {
    return this.findByIdOrNull(id) ?: fail(message)
}