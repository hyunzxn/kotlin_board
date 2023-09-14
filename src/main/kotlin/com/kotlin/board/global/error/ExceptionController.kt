package com.kotlin.board.global.error

import com.kotlin.board.response.ErrorResponse
import com.kotlin.board.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handlingInvalidInputException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.error(e.message, e)
        val errorResponse = ErrorResponse(
            statusCode = 400,
            errorCode = ErrorCode.BAD_REQUEST,
            message = "Invalid Input Value",
        )

        e.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errorResponse.addValidation(fieldName, errorMessage!!)
        }

        return ResponseEntity.badRequest().body(errorResponse)

    }

    @ExceptionHandler(value = [BadCredentialsException::class])
    fun handlingUserNameNotFoundException(e: BadCredentialsException): ResponseEntity<ErrorResponse> {
        log.error(e.message, e)
        val errorResponse = ErrorResponse(
            statusCode = 400,
            errorCode = ErrorCode.BAD_REQUEST,
            message = "아이디 또는 비밀번호를 다시 확인해주세요.",
        )
        return ResponseEntity.badRequest().body(errorResponse)
    }
}