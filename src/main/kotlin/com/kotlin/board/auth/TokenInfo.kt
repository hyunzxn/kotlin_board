package com.kotlin.board.auth

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
)
