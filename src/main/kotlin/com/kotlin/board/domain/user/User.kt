package com.kotlin.board.domain.user

import com.kotlin.board.response.user.UserResponse
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
    name = "USERS",
    uniqueConstraints = [UniqueConstraint(name = "uk_user_login_id", columnNames = ["loginId"])]
)
class User(

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false, length = 30)
    val email: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val userRole: List<UserRole>? = null

    fun toDto(): UserResponse {
        return UserResponse(id!!, loginId, name, birthDate.formatDateToString(), gender.desc, email)
    }

    fun encodePassword(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }

    private fun LocalDate.formatDateToString(): String {
        return this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    companion object {
        fun create(loginId: String, password: String, birthDate: LocalDate, gender: Gender, name: String, email: String): User {
            return User(
                loginId = loginId,
                password = password,
                birthDate = birthDate,
                gender = gender,
                name = name,
                email = email
            )
        }
    }
}