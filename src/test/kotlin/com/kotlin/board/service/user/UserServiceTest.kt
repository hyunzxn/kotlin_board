package com.kotlin.board.service.user

import com.kotlin.board.domain.user.Gender
import com.kotlin.board.domain.user.User
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.request.user.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
class UserServiceTest @Autowired constructor(
    val userService: UserService,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
){

    @Test
    @Transactional
    fun `유저 프로필을 수정할 수 있다`() {
        // given
        val user = User.create(
            loginId = "hyuzxn",
            password = "1234qwer!!",
            birthDate = LocalDate.of(1994, 9, 7),
            gender = Gender.MALE,
            name = "문현준",
            email = "jann1653@gmail.com"
        )
        userRepository.save(user)

        val request = UserUpdateRequest(
            _password = "1234qwer@@",
            _name = "홍길동",
            _email = "jann1653@naver.com"
        )

        // when
        userService.updateInfo(user.id!!, request)

        // then
        assertThat(user.name).isEqualTo("홍길동")
        assertThat(user.email).isEqualTo("jann1653@naver.com")
        assertThat(passwordEncoder.matches("1234qwer@@", user.password)).isTrue()
    }
}