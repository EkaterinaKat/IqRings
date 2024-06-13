package com.katysh.iqrings.view

import android.content.Context

class ScreenScale(context: Context) {

    private val displayMetrics = context.resources.displayMetrics
    val sh = displayMetrics.heightPixels
    val sw = displayMetrics.widthPixels

    fun getWidth(relativeWidth: Float): Float {
        return sw * relativeWidth
    }

    fun getHeight(relativeHeight: Float): Float {
        return sh * relativeHeight
    }
}