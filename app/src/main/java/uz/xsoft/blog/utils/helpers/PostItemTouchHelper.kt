package uz.xsoft.blog.utils.helpers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import uz.xsoft.blog.ui.adapters.PostPageAdapter

class PostItemTouchHelper(private val adapter: PostPageAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onSwiped(viewHolder.bindingAdapterPosition)
    }
}