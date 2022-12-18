package uz.xsoft.blog.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData

interface PostRepository {

    fun loadAllPosts(): Flow<Result<List<PostData>>>

    fun updatePost(postData: PostData): Flow<Result<String>>

    fun getPagedPost(): Flow<PagingData<PostData>>

    fun deletePost(postData: PostData): Flow<Result<String>>

    fun createPost(postData: PostData): Flow<Result<String>>

    fun syncLocalPosts(): Flow<Result<String>>

    suspend fun changeFav(isFav: Boolean, postId: Int)

    fun getFavPosts(): Flow<PagingData<PostData>>

}