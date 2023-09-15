package com.kotlin.board.config.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.board.global.error.ErrorCode
import com.kotlin.board.response.ErrorResponse
import com.kotlin.board.util.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.nio.charset.StandardCharsets
import java.util.logging.Logger

class Http403Handler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {

    private val log = logger()

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {

        log.error("[인증오류] 403")

        val errorResponse = ErrorResponse(
            statusCode = 403,
            errorCode = ErrorCode.FORBIDDEN,
            message = "권한이 없습니다"
        )

        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.characterEncoding = StandardCharsets.UTF_8.name()
        response?.status = HttpServletResponse.SC_FORBIDDEN
        objectMapper.writeValue(response?.writer, errorResponse)
    }
}