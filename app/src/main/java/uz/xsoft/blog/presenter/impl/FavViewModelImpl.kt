package uz.xsoft.blog.presenter.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.domain.usecases.FavUseCase
import uz.xsoft.blog.presenter.FavViewModel
import javax.inject.Inject

@HiltViewModel
class FavViewModelImpl @Inject constructor(private val useCase: FavUseCase) : FavViewModel, ViewModel() {
    override val postFlow: Flow<PagingData<PostData>> = useCase.getPosts().cachedIn(viewModelScope)

    override val openInfoLiveData: MutableLiveData<PostData> = MutableLiveData()

    override fun changeFav(postData: PostData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.changeFav(!postData.fav, postData.postId)
        }
    }

    override fun openInfoScreen(postData: PostData) {
        openInfoLiveData.value = postData
    }
}