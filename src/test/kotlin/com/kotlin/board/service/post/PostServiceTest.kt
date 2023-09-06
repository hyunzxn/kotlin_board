package com.kotlin.board.service.post

import com.kotlin.board.domain.post.PostType
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.request.post.PostCreateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostServiceTest @Autowired constructor(
    val postService: PostService,
    val postRepository: PostRepository,
) {

    @AfterEach
    fun tearDown() {
        postRepository.deleteAllInBatch()
    }

    @Test
    fun `게시글을 작성할 수 있다`() {
        // given
        val request = PostCreateRequest(
            title = "코틀린 잘하고 싶다.",
            content = "그러면 공부 열심히 해야지!",
            type = PostType.FREE
        )

        // when
        postService.save(request)

        // then
        val results = postRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].title).isEqualTo("코틀린 잘하고 싶다.")
        assertThat(results[0].content).isEqualTo("그러면 공부 열심히 해야지!")
        assertThat(results[0].type).isEqualTo(PostType.FREE)
    }
}