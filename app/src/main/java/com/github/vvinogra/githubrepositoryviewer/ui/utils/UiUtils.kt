package com.github.vvinogra.githubrepositoryviewer.ui.utils

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleIf(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        gone()
    }
}
