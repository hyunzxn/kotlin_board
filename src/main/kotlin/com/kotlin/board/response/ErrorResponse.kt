package com.kotlin.board.response

import com.kotlin.board.global.error.ErrorCode

data class ErrorResponse(
    val statusCode: Int,
    val errorCode: ErrorCode,
    val message: String,
)
