package com.kotlin.board.domain.post

import com.kotlin.board.domain.comment.Comment
import com.kotlin.board.domain.user.User
import com.kotlin.board.request.post.PostUpdateRequest
import jakarta.persistence.*

@Entity
class Post(

    var title: String,

    @Lob
    var content: String,

    @Enumerated(EnumType.STRING)
    var type: PostType,

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val comments: MutableList<Comment> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    fun update(request: PostUpdateRequest) {
        this.title = request.title
        this.content = request.content
        this.type = request.type
    }

    companion object {
        fun create(title: String = "게시글 제목", content: String = "게시글 내용", type: PostType = PostType.FREE, user: User): Post {
            return Post(
                title = title,
                content = content,
                type = type,
                user = user
                )
        }
    }
}