package uz.xsoft.blog.data.models.request

data class PostRequest(
    val title: String,
    val body: String,
    val userId: Int
)