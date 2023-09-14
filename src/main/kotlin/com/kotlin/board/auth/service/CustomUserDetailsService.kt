package com.kotlin.board.auth.service

import com.kotlin.board.auth.CustomUser
import com.kotlin.board.domain.user.User
import com.kotlin.board.repository.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByLoginId(username)
            ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("존재하지 않는 유저입니다.")
    }

    private fun createUserDetails(user: User): UserDetails {
        return CustomUser(
            user.id!!,
            user.loginId,
            passwordEncoder.encode(user.password),
            user.userRole!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
    }
}