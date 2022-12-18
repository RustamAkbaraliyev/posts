package uz.xsoft.blog.presenter

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData

interface FavViewModel {
    val postFlow: Flow<PagingData<PostData>>
    val openInfoLiveData: LiveData<PostData>

    fun changeFav(postData: PostData)
    fun openInfoScreen(postData: PostData)


}