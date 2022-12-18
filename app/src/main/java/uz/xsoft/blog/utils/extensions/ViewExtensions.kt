package uz.xsoft.blog.utils.extensions

import android.view.View


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}


fun View.visibleOrGone(state: Boolean) {
    if (state) visible() else gone()
}


fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enableOrDisable(state: Boolean) {
    this.isEnabled = state
}
