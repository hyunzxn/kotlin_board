package com.kotlin.board.service.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.PostType
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.request.post.PostUpdateRequest
import com.kotlin.board.response.post.PostResponse
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.groups.Tuple
import org.assertj.core.groups.Tuple.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `게시글 전체 조회를 할 수 있다`() {
        // given
        postRepository.saveAll(
            listOf(
                Post.create("게시글1", "게시글1", PostType.FREE),
                Post.create("게시글2", "게시글2", PostType.ASK),
                Post.create("게시글3", "게시글3", PostType.NOTICE),
            )
        )

        // when
        val results = postService.getList()

        // then
        assertThat(results).hasSize(3)
            .extracting("title", "content", "type")
            .containsExactlyInAnyOrder(
                tuple("게시글1", "게시글1", PostType.FREE),
                tuple("게시글2", "게시글2", PostType.ASK),
                tuple("게시글3", "게시글3", PostType.NOTICE)
            )
    }

    @Test
    fun `게시글 단건 조회를 할 수 있다`() {
        // given
        val posts = postRepository.saveAll(
            listOf(
                Post.create("게시글1", "게시글1", PostType.FREE),
                Post.create("게시글2", "게시글2", PostType.ASK),
                Post.create("게시글3", "게시글3", PostType.NOTICE),
            )
        )

        // when
        val result = postService.getOne(posts[0].id!!)

        // then
        assertThat(result.title).isEqualTo("게시글1")
        assertThat(result.content).isEqualTo("게시글1")
        assertThat(result.type).isEqualTo(PostType.FREE)
    }

    @Test
    fun `존재하지 않는 게시글을 조회할 수 없다`() {
        // given
        val post = postRepository.save(
            Post.create("게시글 제목", "게시글 내용", PostType.NOTICE)
        )

        // when, then
        assertThrows<IllegalArgumentException> {
            postService.getOne(2L)
        }
    }

    @Test
    fun `게시글을 수정할 수 있다`() {
        // given
        val post = postRepository.save(
            Post.create(
                title = "게시글 제목",
                content = "게시글 내용",
                type = PostType.FREE
            )
        )

        val request = PostUpdateRequest(
            title = "수정 게시글 제목",
            content = "수정 게시글 내용",
            type = PostType.NOTICE
        )

        // when
        val result = postService.update(post.id!!, request)

        // then
        assertThat(result.title).isEqualTo("수정 게시글 제목")
        assertThat(result.content).isEqualTo("수정 게시글 내용")
        assertThat(result.type).isEqualTo(PostType.NOTICE)
    }

    @Test
    fun `게시글을 삭제할 수 있다`() {
        // given
        val post = postRepository.save(
            Post.create("게시글 제목", "게시글 내용", PostType.FREE)
        )

        // when
        postService.delete(post.id!!)

        // then
        val results = postRepository.findAll()
        assertThat(results).isEmpty()
    }
}