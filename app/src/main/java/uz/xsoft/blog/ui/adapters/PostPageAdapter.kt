package uz.xsoft.blog.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.xsoft.blog.R
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.data.models.common.State
import uz.xsoft.blog.databinding.ItemPostBinding

class PostPageAdapter : PagingDataAdapter<PostData, PostPageAdapter.Holder>(ItemCallBack) {
    private var listener: ((PostData) -> Unit)? = null
    private var swipeListener: ((PostData) -> Unit)? = null
    private var favListener: ((PostData) -> Unit)? = null
    private var editListener: ((PostData) -> Unit)? = null

    object ItemCallBack : DiffUtil.ItemCallback<PostData>() {
        override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean = oldItem == newItem
    }

    inner class Holder(private val itemPostBinding: ItemPostBinding) : ViewHolder(itemPostBinding.root) {
        init {
            itemPostBinding.root.setOnClickListener { getItem(bindingAdapterPosition)?.let { it1 -> listener?.invoke(it1) } }
            itemPostBinding.root.setOnLongClickListener {
                getItem(bindingAdapterPosition)?.let { it1 -> editListener?.invoke(it1) }
                true
            }
            itemPostBinding.btnFav.setOnClickListener { getItem(bindingAdapterPosition)?.let { it1 -> favListener?.invoke(it1) } }
        }

        fun bind() {
            val item = getItem(bindingAdapterPosition) ?: return
            itemPostBinding.txtDescription.text = item.body
            itemPostBinding.txtTitle.text = item.title
            itemPostBinding.progressCircular.visibility = View.INVISIBLE
            itemPostBinding.btnFav.setImageResource(if (item.fav) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
            when (item.state) {
                State.SYNCED -> itemPostBinding.imgStatus.setImageResource(R.drawable.ic_bullet)
                State.LOCAL -> itemPostBinding.imgStatus.setImageResource(R.drawable.ic_refresh_new)
                else -> {
                    itemPostBinding.imgStatus.visibility = View.INVISIBLE
                    itemPostBinding.progressCircular.visibility = View.VISIBLE

                }
            }
        }
    }

    fun onSwiped(position: Int) {
        getItem(position)?.let { swipeListener?.invoke(it) }
    }

    fun setOnItemClickListener(l: (PostData) -> Unit) {
        listener = l
    }

    fun setOnSwipeListener(l: (PostData) -> Unit) {
        swipeListener = l
    }

    fun setOnItemLongClickListener(l: (PostData) -> Unit) {
        editListener = l
    }

    fun setChangeFavListener(l: (PostData) -> Unit) {
        favListener = l
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}