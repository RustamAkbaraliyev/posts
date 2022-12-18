package uz.xsoft.blog.domain.usecases.impl

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.domain.repositories.PostRepository
import uz.xsoft.blog.domain.usecases.FavUseCase
import javax.inject.Inject

class FavUseCaseImpl @Inject constructor(private val repository: PostRepository) : FavUseCase {

    override fun getPosts(): Flow<PagingData<PostData>> = repository.getFavPosts()

    override suspend fun changeFav(isFav: Boolean, postId: Int) = repository.changeFav(isFav, postId)
}