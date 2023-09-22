package com.kotlin.board.domain.comment

import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.user.User
import jakarta.persistence.*

@Entity
class Comment(
    var content: String,

    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "fk_comment_post_id"))
    val post: Post,

    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "fk_comment_user_id"))
    val user: User,

    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "fk_parent_comment_id"))
    var parent: Comment? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val children: MutableList<Comment> = mutableListOf()

    companion object {
        fun create(content: String = "댓글 내용", post: Post, user: User): Comment {
            return Comment(
                content = content,
                post = post,
                user = user
            )
        }

        fun createReComment(content: String, post: Post, parent: Comment, user: User): Comment {
            return Comment(
                content = content,
                post = post,
                parent = parent,
                user = user
            )
        }
    }
}