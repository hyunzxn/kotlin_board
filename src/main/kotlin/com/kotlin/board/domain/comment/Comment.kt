package com.kotlin.board.domain.comment

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.user.User
import jakarta.persistence.*

@Entity
class Comment(
    var content: String,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    companion object {
        fun create(content: String = "댓글 내용", post: Post, user: User): Comment {
            return Comment(
                content = content,
                post = post,
                user = user
            )
        }
    }
}