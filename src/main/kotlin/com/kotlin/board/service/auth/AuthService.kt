package com.kotlin.board.service.auth

import com.kotlin.board.domain.user.User
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.request.auth.SignupRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun signup(request: SignupRequest): String {
        var user: User? = userRepository.findByLoginId(request.loginId)
        if (user != null) {
            throw IllegalArgumentException("이미 등록된 ID 입니다.")
        }

        user = request.toEntity()

        userRepository.save(user)

        return "회원가입이 완료되었습니다."
    }
}