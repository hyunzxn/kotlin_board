package com.kotlin.board.controller.user

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.request.user.UserUpdateRequest
import com.kotlin.board.response.user.UserResponse
import com.kotlin.board.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {

    @PreAuthorize("isAuthenticated() && hasAnyRole('MEMBER')")
    @GetMapping("/info")
    fun getMyInfo(): ResponseEntity<UserResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity.ok().body(userService.getMyInfo(userId))
    }

    @PreAuthorize("isAuthenticated() && hasAnyRole('MEMBER')")
    @PutMapping("/profile")
    fun updateInfo(@RequestBody request: UserUpdateRequest): ResponseEntity<UserResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity.ok().body(userService.updateInfo(userId, request))

    }
}