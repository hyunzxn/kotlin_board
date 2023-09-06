package com.kotlin.board.domain.post

import jakarta.persistence.*

@Entity
class Post(

    var title: String,

    @Lob
    var content: String,

    @Enumerated(EnumType.STRING)
    val type: PostType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    companion object {
        fun create(title: String = "게시글 제목", content: String = "게시글 내용", type: PostType = PostType.FREE): Post {
            return Post(
                title = title,
                content = content,
                type = type,

            )
        }
    }
}