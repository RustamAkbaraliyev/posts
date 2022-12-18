package uz.xsoft.blog.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.xsoft.blog.R
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.databinding.ScreenFavBinding
import uz.xsoft.blog.presenter.FavViewModel
import uz.xsoft.blog.presenter.impl.FavViewModelImpl
import uz.xsoft.blog.ui.adapters.PostPageAdapter
import uz.xsoft.blog.utils.extensions.visibleOrGone

@AndroidEntryPoint
class FavScreen : Fragment(R.layout.screen_fav) {
    private val viewBinding: ScreenFavBinding by viewBinding(ScreenFavBinding::bind)
    private val viewModel: FavViewModel by viewModels<FavViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val adapter: PostPageAdapter by lazy { PostPageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openInfoLiveData.observe(this, openInfoObserver)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.list.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        viewBinding.list.addItemDecoration(dividerItemDecoration)
        adapter.setOnItemClickListener { viewModel.openInfoScreen(it) }
        adapter.setChangeFavListener { viewModel.changeFav(it) }
        viewModel.postFlow.onEach { adapter.submitData(it) }.launchIn(lifecycleScope)
        adapter.onPagesUpdatedFlow.onEach {
            viewBinding.animationView.visibleOrGone(adapter.itemCount == 0)
        }.launchIn(lifecycleScope)
    }


    private val openInfoObserver = Observer<PostData> { navController.navigate(FavScreenDirections.actionFavScreenToInfoScreen(it)) }

}