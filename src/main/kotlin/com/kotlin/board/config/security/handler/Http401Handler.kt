package com.kotlin.board.config.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.board.global.error.ErrorCode
import com.kotlin.board.response.ErrorResponse
import com.kotlin.board.util.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.nio.charset.StandardCharsets

class Http401Handler(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {

    private val log = logger()

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {

        log.error("[인증오류] 로그인이 필요합니다", )

        val errorResponse = ErrorResponse(
            statusCode = 401,
            errorCode = ErrorCode.UNAUTHORIZED,
            message = "로그인이 필요합니다."
        )

        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.characterEncoding = StandardCharsets.UTF_8.name()
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        objectMapper.writeValue(response?.writer, errorResponse)

    }
}