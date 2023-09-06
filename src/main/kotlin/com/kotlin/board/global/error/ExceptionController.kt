package com.kotlin.board.global.error

import com.kotlin.board.response.ErrorResponse
import com.kotlin.board.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    val log = logger()

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handlingRuntimeException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        log.error(e.message, e)
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                statusCode = 400,
                errorCode = ErrorCode.BAD_REQUEST,
                message = e.message!!
            )
        )
    }
}