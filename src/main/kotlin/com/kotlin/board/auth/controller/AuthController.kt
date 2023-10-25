package com.kotlin.board.auth.controller

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.auth.TokenInfo
import com.kotlin.board.request.auth.LoginRequest
import com.kotlin.board.request.auth.SignupRequest
import com.kotlin.board.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/signup")
    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<String> {
        return ResponseEntity.ok().body(authService.signup(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<TokenInfo> {
        return ResponseEntity.ok().body(authService.login(request))
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") bearerToken: String): ResponseEntity<String> {
        val accessToken = bearerToken.substring(7)
        return ResponseEntity.ok().body(authService.logout(accessToken))
    }
}