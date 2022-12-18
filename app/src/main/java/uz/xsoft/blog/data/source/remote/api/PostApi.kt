package uz.xsoft.blog.data.source.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import uz.xsoft.blog.data.models.request.PostRequest
import uz.xsoft.blog.data.models.response.PostResponse

interface PostApi {

    @POST("posts")
    suspend fun addPost(@Body postRequest: PostRequest): Response<PostResponse>

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>

    @DELETE("posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int): Response<ResponseBody>

    @PUT("posts/{postId}")
    suspend fun updatePost(@Path("postId") postId: Int, @Body postRequest: PostRequest): Response<PostResponse>
}