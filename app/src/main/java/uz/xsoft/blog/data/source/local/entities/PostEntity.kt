package uz.xsoft.blog.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.data.models.common.State
import uz.xsoft.blog.data.models.request.PostRequest

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val postId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val state: State = State.LOCAL,
    val fav: Boolean = false
)


fun PostEntity.toData() : PostData = PostData(postId, id, title, body, userId, state, fav)

fun PostEntity.toRequest(): PostRequest = PostRequest(title, body, userId)