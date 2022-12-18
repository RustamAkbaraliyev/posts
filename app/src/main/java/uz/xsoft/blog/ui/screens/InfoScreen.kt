package uz.xsoft.blog.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.xsoft.blog.R
import uz.xsoft.blog.databinding.ScreenInfoBinding

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val viewBinding: ScreenInfoBinding by viewBinding(ScreenInfoBinding::bind)
    private val args: InfoScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.txtBody.text = args.postData.body
        viewBinding.txtTitle.text = args.postData.title
    }
}