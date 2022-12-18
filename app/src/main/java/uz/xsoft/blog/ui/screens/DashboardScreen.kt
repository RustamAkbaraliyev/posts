package uz.xsoft.blog.ui.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.xsoft.blog.R
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.databinding.ScreenDashboardBinding
import uz.xsoft.blog.presenter.DashboardViewModel
import uz.xsoft.blog.presenter.impl.DashboardViewModelImpl
import uz.xsoft.blog.ui.adapters.PostPageAdapter
import uz.xsoft.blog.ui.dialogs.PostDialog
import uz.xsoft.blog.utils.extensions.enableOrDisable
import uz.xsoft.blog.utils.extensions.errorMessage
import uz.xsoft.blog.utils.extensions.successMessage
import uz.xsoft.blog.utils.extensions.visibleOrGone
import uz.xsoft.blog.utils.helpers.PostItemTouchHelper

@AndroidEntryPoint
class DashboardScreen : Fragment(R.layout.screen_dashboard) {
    private val viewBinding: ScreenDashboardBinding by viewBinding(ScreenDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels<DashboardViewModelImpl>()
    private val adapter: PostPageAdapter by lazy { PostPageAdapter() }
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadPosts()
        viewModel.addLiveData.observe(this, addObserver)
        viewModel.editLiveData.observe(this, editObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.openInfoLiveData.observe(this, openInfoObserver)
        viewModel.openFavLiveData.observe(this, openFavObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMenu()
        viewBinding.list.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        viewBinding.list.addItemDecoration(dividerItemDecoration)

        adapter.setOnItemLongClickListener { viewModel.openDialogForEdit(it) }
        adapter.setOnSwipeListener { viewModel.deletePost(it) }
        adapter.setChangeFavListener { viewModel.changeFav(it) }
        adapter.setOnItemClickListener { viewModel.openInfoScreen(it) }

        lifecycleScope.launchWhenResumed {
            adapter.onPagesUpdatedFlow.collectLatest {
                viewBinding.animationView.visibleOrGone(adapter.itemCount == 0)
            }
        }
        val itemTouchCallback = PostItemTouchHelper(adapter)
        val itemTouch = ItemTouchHelper(itemTouchCallback)
        itemTouch.attachToRecyclerView(viewBinding.list)
        viewBinding.btnAdd.setOnClickListener { viewModel.openAddDialog() }

        viewModel.postFlow.onEach { adapter.submitData(it) }.launchIn(lifecycleScope)

        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }


    private val loadingObserver = Observer<Boolean> {
        viewBinding.progressLinear.visibleOrGone(it)
        viewBinding.btnAdd.enableOrDisable(!it)
    }

    private val messageObserver = Observer<String> { errorMessage(it) }
    private val successObserver = Observer<String> { successMessage(it) }
    private val openInfoObserver = Observer<PostData> { navController.navigate(DashboardScreenDirections.actionDashboardScreenToInfoScreen(it)) }
    private val openFavObserver = Observer<Unit> { navController.navigate(DashboardScreenDirections.actionDashboardScreenToFavScreen()) }
    private val addObserver = Observer<Unit> {
        PostDialog().apply {
            setOnSaveClickListener { viewModel.addPost(it) }
        }.show(childFragmentManager, null)
    }

    private val editObserver = Observer<PostData> {
        PostDialog().apply {
            val bundle = Bundle()
            bundle.putSerializable("POST_DATA", it)
            arguments = bundle
            setOnSaveClickListener { viewModel.updatePost(it) }
        }.show(childFragmentManager, null)
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_dashboard, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionFav -> {
                        viewModel.openFavScreen()
                        true
                    }
                    R.id.actionUpload -> {
                        viewModel.syncLocal()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}
