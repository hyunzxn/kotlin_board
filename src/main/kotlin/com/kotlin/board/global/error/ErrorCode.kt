package com.kotlin.board.global.error

enum class ErrorCode(
    private val code: Int,
    private val description: String,
) {

    BAD_REQUEST(400,"잘못된 요청입니다."),
    UNAUTHORIZED(401, "인증되지 않은 사용자입니다."),
    FORBIDDEN(403, "권한이 없는 요청입니다."),
    NOT_FOUND(404, "요청한 바를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다."),

}