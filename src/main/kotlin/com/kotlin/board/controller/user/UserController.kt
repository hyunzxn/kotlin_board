package com.kotlin.board.controller.user

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.response.user.UserResponse
import com.kotlin.board.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/info")
    fun getMyInfo(): ResponseEntity<UserResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity.ok().body(userService.getMyInfo(userId))
    }
}