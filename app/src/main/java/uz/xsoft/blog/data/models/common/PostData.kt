package uz.xsoft.blog.data.models.common

import uz.xsoft.blog.data.models.request.PostRequest
import uz.xsoft.blog.data.source.local.entities.PostEntity

data class PostData(
    val postId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val state: State = State.LOCAL,
    val fav: Boolean = false
) : java.io.Serializable


fun PostData.toRequest(): PostRequest = PostRequest(title, body, userId)

fun PostData.toEntity(): PostEntity = PostEntity(postId, id, title, body, userId, state, fav)