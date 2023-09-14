package com.kotlin.board.request.auth

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {

    val loginId: String
        get() = _loginId!!

    val password: String
        get() = _password!!

}
