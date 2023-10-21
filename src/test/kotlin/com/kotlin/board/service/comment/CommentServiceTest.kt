package com.kotlin.board.service.comment

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.post.PostType
import com.kotlin.board.domain.user.Gender
import com.kotlin.board.domain.user.User
import com.kotlin.board.repository.comment.CommentRepository
import com.kotlin.board.repository.post.PostRepository
import com.kotlin.board.repository.user.UserRepository
import com.kotlin.board.request.comment.CommentCreateRequest
import com.kotlin.board.util.PagingUtil
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
class CommentServiceTest @Autowired constructor(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
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
        commentRepository.deleteAll()
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `댓글을 작성할 수 있다`() {
        // given
        val post = Post.create("게시글 제목", "게시글 내용", PostType.FREE, user)
        postRepository.save(post)

        val reqeust = CommentCreateRequest(post.id!!, "댓글 내용!", null)

        // when
        commentService.save(reqeust, user.id!!)

        // then
        val results = commentRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].content).isEqualTo("댓글 내용!")
    }

    @Test
    fun `대댓글을 작성할 수 있다`() {
        // given
        val post = Post.create("게시글 제목", "게시글 내용", PostType.FREE, user)
        postRepository.save(post)

        val comment = Comment.create("처음 달린 댓글", user)
        comment.post = post
        commentRepository.save(comment)

        val request = CommentCreateRequest(
            postId = post.id!!,
            content = "대댓글입니다.",
            parentId = comment.id
        )

        val pagingUtil = PagingUtil()

        // when
        commentService.save(request, user.id!!)

        // then
        val result = commentService.getComments(post.id!!, pagingUtil)
        assertThat(result[0].reComments).isNotEmpty()
    }

    @Test
    fun `댓글을 삭제할 수 있다`() {
        // given
        val post = Post.create("게시글 제목", "게시글 내용", PostType.FREE, user)
        postRepository.save(post)

        val comment = Comment.create("댓글 댓글", user)
        comment.post = post
        commentRepository.save(comment)

        // when
        commentService.delete(comment.id!!)

        // then
        val results = commentRepository.findAll()
        assertThat(results).isEmpty()
    }

    @Test
    fun `댓글을 페이징 처리하여 조회할 수 있다`() {
        // given
        val post = Post.create("게시글 제목", "게시글 내용", PostType.FREE, user)
        postRepository.save(post)

        val comments = mutableListOf<Comment>()
        (1..20).map {
            val comment = Comment.create("댓글 내용 $it", user)
            comment.post = post
            comments.add(comment)
        }
        commentRepository.saveAll(comments)

        val pagingUtil = PagingUtil()

        // when
        val results = commentService.getComments(post.id!!, pagingUtil)

        // then
        assertThat(results).hasSize(10)
        assertThat(results[0].content).isEqualTo("댓글 내용 1")
        assertThat(results[9].content).isEqualTo("댓글 내용 10")
    }
}