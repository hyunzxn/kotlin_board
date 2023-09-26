package com.kotlin.board.response.user

import com.kotlin.board.domain.user.User
import com.kotlin.board.util.formatDateToString

data class UserResponse(
    val id: Long,
    val loginId: String,
    val name: String,
    val birthDate: String,
    val gender: String,
    val email: String,
) {

    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                loginId = user.loginId,
                name = user.name,
                birthDate = user.birthDate.formatDateToString(),
                gender = user.gender.toString(),
                email = user.email
            )
        }
    }
}
