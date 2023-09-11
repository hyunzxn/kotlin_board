package com.kotlin.board.response

import com.kotlin.board.global.error.ErrorCode

data class ErrorResponse(
    val statusCode: Int,
    val errorCode: ErrorCode,
    val message: String,
    val invalidInputValue: MutableMap<String, String>? = HashMap<String, String>(),
) {

    fun addValidation(fieldName: String, message: String) {
        this.invalidInputValue?.put(fieldName, message)
    }
}
