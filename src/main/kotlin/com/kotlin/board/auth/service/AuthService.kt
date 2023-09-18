package com.kotlin.board.auth.service

import com.kotlin.board.auth.TokenInfo
import com.kotlin.board.auth.jwt.JwtTokenProvider
import com.kotlin.board.domain.user.ROLE
import com.kotlin.board.domain.user.User
import com.kotlin.board.domain.user.UserRole
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.repository.user.UserRoleRepository
import com.kotlin.board.request.auth.LoginRequest
import com.kotlin.board.request.auth.SignupRequest
import com.kotlin.board.util.findByIdOrThrow
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun signup(request: SignupRequest): String {
        var user: User? = userRepository.findByLoginId(request.loginId)
        if (user != null) {
            throw IllegalArgumentException("이미 등록된 ID 입니다.")
        }

        user = request.toEntity()
        user.encodePassword(passwordEncoder)

        userRepository.save(user)

        val userRole = UserRole(ROLE.MEMBER, user, null)
        userRoleRepository.save(userRole)

        return "회원가입이 완료되었습니다."
    }

    @Transactional
    fun login(request: LoginRequest): TokenInfo {
        val loginUser = userRepository.findByLoginId(request.loginId)

        if (!isLoginValidated(request, loginUser)) {
            throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다. 아이디 또는 비밀번호를 다시 확인해주세요.")
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(request.loginId, loginUser?.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    private fun isLoginValidated(request: LoginRequest, user: User?): Boolean {
        return request.loginId == user?.loginId && passwordEncoder.matches(request.password, user.password)
    }
}