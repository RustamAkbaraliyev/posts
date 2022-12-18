package uz.xsoft.blog.domain.usecases.impl

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.domain.repositories.PostRepository
import uz.xsoft.blog.domain.usecases.DashboardUseCase
import javax.inject.Inject

class DashboardUseCaseImpl @Inject constructor(private val repository: PostRepository) : DashboardUseCase {

    override fun getAllPosts(): Flow<PagingData<PostData>> = repository.getPagedPost()

    override fun updatePost(postData: PostData): Flow<Result<String>> = repository.updatePost(postData)

    override fun deletePost(postData: PostData): Flow<Result<String>> = repository.deletePost(postData)

    override fun loadPosts(): Flow<Result<List<PostData>>> = repository.loadAllPosts()

    override fun addPost(postData: PostData): Flow<Result<String>> = repository.createPost(postData)

    override fun sync(): Flow<Result<String>> = repository.syncLocalPosts()

    override suspend fun changeFav(isFav: Boolean, postId: Int) = repository.changeFav(isFav, postId)
}