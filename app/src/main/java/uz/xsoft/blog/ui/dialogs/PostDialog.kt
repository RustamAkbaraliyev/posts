package uz.xsoft.blog.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.xsoft.blog.R
import uz.xsoft.blog.data.models.common.PostData
import uz.xsoft.blog.databinding.DialogPostBinding
import uz.xsoft.blog.utils.extensions.setWidthPercent

class PostDialog : DialogFragment(R.layout.dialog_post) {
    private val viewBinding: DialogPostBinding by viewBinding(DialogPostBinding::bind)
    private var listener: ((PostData) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setWidthPercent(90)

        val postData = arguments?.getSerializable("POST_DATA") as? PostData
        viewBinding.inputTitle.setText(postData?.title)
        viewBinding.inputBody.setText(postData?.body)

        combine(
            viewBinding.inputTitle.textChanges().map { it.trim().isNotEmpty() },
            viewBinding.inputBody.textChanges().map { it.trim().isNotEmpty() },
            transform = { title, body -> title && body }
        )
            .onEach { viewBinding.btnSave.isEnabled = it }
            .launchIn(lifecycleScope)

        viewBinding.btnSave.setOnClickListener {
            val title = viewBinding.inputTitle.text.toString().trim()
            val body = viewBinding.inputBody.text.toString().trim()
            val resultPostData = PostData(0, postData?.id ?: 0, title, body, title.length)
            listener?.invoke(resultPostData)
            dismiss()
        }
    }

    fun setOnSaveClickListener(l: (PostData) -> Unit) {
        listener = l
    }
}