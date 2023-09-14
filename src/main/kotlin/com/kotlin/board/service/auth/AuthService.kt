package com.kotlin.board.service.auth

import com.kotlin.board.auth.TokenInfo
import com.kotlin.board.auth.jwt.JwtTokenProvider
import com.kotlin.board.domain.user.ROLE
import com.kotlin.board.domain.user.User
import com.kotlin.board.domain.user.UserRole
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.repository.user.UserRoleRepository
import com.kotlin.board.request.auth.LoginRequest
import com.kotlin.board.request.auth.SignupRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Transactional
    fun signup(request: SignupRequest): String {
        var user: User? = userRepository.findByLoginId(request.loginId)
        if (user != null) {
            throw IllegalArgumentException("이미 등록된 ID 입니다.")
        }

        user = request.toEntity()

        userRepository.save(user)

        val userRole: UserRole = UserRole(ROLE.MEMBER, user, null)
        userRoleRepository.save(userRole)

        return "회원가입이 완료되었습니다."
    }

    @Transactional
    fun login(request: LoginRequest): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(request.loginId, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }
}