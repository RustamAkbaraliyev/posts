package uz.xsoft.blog.utils.extensions

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.tapadoo.alerter.Alerter
import uz.xsoft.blog.R


fun Fragment.errorMessage(message: String) {
    Alerter.create(requireActivity())
        .setTitle(message)
        .setDuration(1000)
        .setBackgroundColorRes(R.color.blue)
        .setIcon(R.drawable.ic_baseline_error_24)
        .setIconColorFilter(Color.RED)
        .show()
}


fun Fragment.successMessage(message: String) {
    Alerter.create(requireActivity())
        .setTitle(message)
        .setDuration(1000)
        .setIconColorFilter(Color.GREEN)
        .setBackgroundColorRes(R.color.blue)
        .setIcon(R.drawable.ic_baseline_check_circle_24)
        .show()
}