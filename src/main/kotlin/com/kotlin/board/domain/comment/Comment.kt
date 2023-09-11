package com.kotlin.board.domain.comment

import com.kotlin.board.domain.post.Post
import jakarta.persistence.*

@Entity
class Comment(
    var content: String,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    companion object {
        fun create(content: String = "댓글 내용", post: Post): Comment {
            return Comment(
                content = content,
                post = post,
            )
        }
    }
}