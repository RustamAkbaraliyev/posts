package uz.xsoft.blog.data.models.response

import uz.xsoft.blog.data.models.common.State
import uz.xsoft.blog.data.source.local.entities.PostEntity

data class PostResponse(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)


fun PostResponse.toEntity(state: State = State.LOCAL): PostEntity = PostEntity(0, id, title, body, userId, state)