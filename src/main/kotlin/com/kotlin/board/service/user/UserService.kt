package com.kotlin.board.service.user

import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.response.user.UserResponse
import com.kotlin.board.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {

    fun getMyInfo(id: Long): UserResponse {
        return userRepository.findByIdOrThrow(id, "존재하지 않는 회원입니다.").toDto()
    }

}