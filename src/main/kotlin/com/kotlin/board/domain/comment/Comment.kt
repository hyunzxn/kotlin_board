package com.kotlin.board.domain.comment

import com.kotlin.board.domain.common.TimeStamp
import com.kotlin.board.domain.post.Post
import com.kotlin.board.domain.user.User
import jakarta.persistence.*

@Entity
class Comment(
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_comment_user_id"))
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_comment_post_id"))
    var post: Post? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_parent_comment_id"))
    var parent: Comment? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : TimeStamp() {

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val children: MutableList<Comment> = mutableListOf()

    companion object {
        fun create(content: String = "댓글 내용", user: User): Comment {
            return Comment(
                content = content,
                user = user
            )
        }

        fun createReComment(content: String, parent: Comment, user: User): Comment {
            return Comment(
                content = content,
                parent = parent,
                user = user
            )
        }
    }
}