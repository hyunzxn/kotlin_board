package com.kotlin.board.repository.user

import com.kotlin.board.domain.user.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRole, Long>