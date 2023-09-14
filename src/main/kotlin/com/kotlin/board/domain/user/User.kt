package com.kotlin.board.domain.user

import com.kotlin.board.response.user.UserResponse
import jakarta.persistence.*
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
    val password: String,

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

    private fun LocalDate.formatDateToString(): String {
        return this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }
}