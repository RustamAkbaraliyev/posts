package uz.xsoft.blog.domain.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData

interface FavUseCase {
    fun getPosts(): Flow<PagingData<PostData>>
    suspend fun changeFav(isFav: Boolean, postId:Int)

}