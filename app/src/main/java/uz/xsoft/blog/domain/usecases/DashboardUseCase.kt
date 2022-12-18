package uz.xsoft.blog.domain.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData

interface DashboardUseCase {

    fun getAllPosts(): Flow<PagingData<PostData>>

    fun updatePost(postData: PostData): Flow<Result<String>>

    fun deletePost(postData: PostData): Flow<Result<String>>

    fun loadPosts(): Flow<Result<List<PostData>>>

    fun addPost(postData: PostData): Flow<Result<String>>

    fun sync(): Flow<Result<String>>

    suspend fun changeFav(isFav: Boolean, postId:Int)

}