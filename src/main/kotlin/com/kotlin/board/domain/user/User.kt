package com.kotlin.board.domain.user

import com.kotlin.board.request.user.UserUpdateRequest
import com.kotlin.board.response.user.UserResponse
import com.kotlin.board.util.formatDateToString
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
    var loginId: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, length = 10)
    var name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false, length = 30)
    var email: String,

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

    fun updateInfo(request: UserUpdateRequest, passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(request.password)
        this.name = request.name
        this.email = request.email
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