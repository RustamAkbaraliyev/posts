package uz.xsoft.blog.presenter.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.domain.usecases.DashboardUseCase
import uz.xsoft.blog.presenter.DashboardViewModel
import javax.inject.Inject


@HiltViewModel
class DashboardViewModelImpl @Inject constructor(private val useCase: DashboardUseCase) : DashboardViewModel, ViewModel() {

    override val postFlow: Flow<PagingData<PostData>> = useCase.getAllPosts().cachedIn(viewModelScope)
    override val messageLiveData: MutableLiveData<String> = MutableLiveData()
    override val successLiveData: MutableLiveData<String> = MutableLiveData()
    override val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    override val addLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val editLiveData: MutableLiveData<PostData> = MutableLiveData()
    override val openInfoLiveData: MutableLiveData<PostData> = MutableLiveData()
    override val openFavLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun addPost(postData: PostData) {
        loadingLiveData.value = true
        useCase.addPost(postData)
            .onEach { loadingLiveData.value = false }
            .onEach { it.onSuccess { successLiveData.value = it } }
            .onEach { it.onFailure { messageLiveData.value = it.message } }
            .launchIn(viewModelScope)
    }

    override fun deletePost(postData: PostData) {
        loadingLiveData.value = true
        useCase.deletePost(postData)
            .onEach { loadingLiveData.value = false }
            .onEach { it.onSuccess { successLiveData.value = it } }
            .onEach { it.onFailure { messageLiveData.value = it.message } }
            .launchIn(viewModelScope)
    }

    override fun updatePost(postData: PostData) {
        loadingLiveData.value = true
        useCase.updatePost(postData)
            .onEach { loadingLiveData.value = false }
            .onEach { it.onSuccess { successLiveData.value = it } }
            .onEach { it.onFailure { messageLiveData.value = it.message } }
            .launchIn(viewModelScope)
    }

    override fun loadPosts() {
        loadingLiveData.value = true
        useCase.loadPosts()
            .onEach { loadingLiveData.value = false }
            .onEach { it.onFailure { messageLiveData.value = it.message } }
            .launchIn(viewModelScope)
    }

    override fun openAddDialog() {
        addLiveData.value = Unit
    }

    override fun openDialogForEdit(postData: PostData) {
        editLiveData.value = postData
    }

    override fun syncLocal() {

        useCase.sync()
            .onEach { it.onSuccess { successLiveData.value = it } }
            .onEach { it.onFailure { messageLiveData.value = it.message } }
            .launchIn(viewModelScope)

    }

    override fun changeFav(postData: PostData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.changeFav(!postData.fav, postData.postId)
        }
    }

    override fun openFavScreen() {
        openFavLiveData.value = Unit
    }

    override fun openInfoScreen(postData: PostData) {
        openInfoLiveData.value = postData
    }
}