package com.kotlin.board.service.post

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.PostType
import com.kotlin.board.domain.post.postlike.PostLike
import com.kotlin.board.domain.user.Gender
import com.kotlin.board.domain.user.User
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.repository.post.postlike.PostLikeRepository
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.request.post.PostCreateRequest
import com.kotlin.board.request.post.PostUpdateRequest
import com.kotlin.board.util.PagingUtil
import com.kotlin.board.util.findByIdOrThrow
import jakarta.persistence.EntityManager
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
class PostServiceTest @Autowired constructor(
    val postService: PostService,
    val postRepository: PostRepository,
    val userRepository: UserRepository,
    val postLikeRepository: PostLikeRepository,
) {

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User.create(
            loginId = "hyuzxn",
            password = "1234qwer!!",
            birthDate = LocalDate.of(1994, 9, 7),
            gender = Gender.MALE,
            name = "문현준",
            email = "jann1653@gmail.com"
        )
        userRepository.save(user)
    }

    @AfterEach
    fun tearDown() {
        postLikeRepository.deleteAll()
        postRepository.deleteAll()
        userRepository.deleteAll()
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
        postService.save(request, user.id!!)

        // then
        val results = postRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].title).isEqualTo("코틀린 잘하고 싶다.")
        assertThat(results[0].content).isEqualTo("그러면 공부 열심히 해야지!")
        assertThat(results[0].type).isEqualTo(PostType.FREE)
    }

    @Test
    fun `게시글 전체 조회 시 페이징 처리가 돼서 조회할 수 있다`() {
        // given
        val posts = mutableListOf<Post>()
        (1..20).map {
            val post = Post.create("게시글 제목 $it", "게시글 내용 $it", PostType.FREE, user)
            posts.add(post)
        }
        postRepository.saveAll(posts)

        val pagingUtil = PagingUtil()

        // when
        val results = postService.getListWithPaging(pagingUtil)

        // then
        assertThat(results).hasSize(10)
        assertThat(results[0].title).isEqualTo("게시글 제목 20")
        assertThat(results[9].title).isEqualTo("게시글 제목 11")
    }

    @Test
    fun `게시글 단건 조회를 할 수 있다`() {
        // given
        val posts = postRepository.saveAll(
            listOf(
                Post.create("게시글1", "게시글1", PostType.FREE, user),
                Post.create("게시글2", "게시글2", PostType.ASK, user),
                Post.create("게시글3", "게시글3", PostType.NOTICE, user),
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

        // when, then
        assertThrows<IllegalArgumentException> {
            postService.getOne(100L)
        }
    }

    @Test
    fun `게시글을 수정할 수 있다`() {
        // given
        val post = postRepository.save(
            Post.create("게시글 제목", "게시글 내용", PostType.FREE, user)
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
            Post.create("게시글 제목", "게시글 내용", PostType.FREE, user)
        )

        // when
        postService.delete(post.id!!)

        // then
        val results = postRepository.findAll()
        assertThat(results).isEmpty()
    }

    @Test
    fun `게시글에 좋아요를 누르면 좋아요 개수가 1 증가한다`() {
        // given
        val post = Post.create(
            title = "게시글 제목",
            content = "게시글 내용",
            type = PostType.FREE,
            user = user
        )
        postRepository.save(post)

        // when
        postService.postLike(user.id!!, post.id!!)

        // then
        val result = postRepository.findByIdOrThrow(post.id!!, "존재하지 않는 게시글")
        assertThat(result.likeCount).isEqualTo(1)
    }

    @Test
    fun `이미 좋아요 된 게시글 좋아요를 한 번 더 하면 좋아요가 취소된다`() {
        // given
        val post = Post.create(
            title = "게시글 제목",
            content = "게시글 내용",
            type = PostType.FREE,
            user = user
        )
        post.likeCount = 1 // 이미 좋아요가 된 post 이기 때문에 likeCount를 1로 설정
        postRepository.save(post)

        val postLike = PostLike.create(user, post)
        postLikeRepository.save(postLike)

        // when
        postService.postLike(user.id!!, post.id!!)

        // then
        val result = postRepository.findByIdOrThrow(post.id!!, "존재하지 않는 게시글")
        assertThat(result.likeCount).isEqualTo(0)
    }
}