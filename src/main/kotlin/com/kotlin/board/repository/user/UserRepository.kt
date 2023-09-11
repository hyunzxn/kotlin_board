package com.kotlin.board.repository.user

import com.kotlin.board.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByLoginId(loginId: String): User?
}