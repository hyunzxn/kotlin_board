package com.kotlin.board.domain.post.postlike

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.user.User
import jakarta.persistence.*

@Entity
class PostLike(

    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "fk_post_like_user_id"))
    val user: User,

    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "fk_post_like_post_id"))
    val post: Post,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    companion object {
        fun create(user: User, post: Post): PostLike {
            return PostLike(
                user = user,
                post = post
            )
        }
    }
}