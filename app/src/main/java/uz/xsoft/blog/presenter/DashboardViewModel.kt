package uz.xsoft.blog.presenter

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.xsoft.blog.data.models.common.PostData

interface DashboardViewModel {
    val postFlow: Flow<PagingData<PostData>>
    val messageLiveData: LiveData<String>
    val successLiveData: LiveData<String>
    val loadingLiveData: LiveData<Boolean>
    val addLiveData: LiveData<Unit>
    val editLiveData: LiveData<PostData>
    val openInfoLiveData: LiveData<PostData>
    val openFavLiveData: LiveData<Unit>

    fun addPost(postData: PostData)
    fun deletePost(postData: PostData)
    fun updatePost(postData: PostData)
    fun loadPosts()

    fun openAddDialog()

    fun openDialogForEdit(postData: PostData)

    fun openFavScreen()

    fun openInfoScreen(postData: PostData)

    fun syncLocal()

    fun changeFav(postData: PostData)


}