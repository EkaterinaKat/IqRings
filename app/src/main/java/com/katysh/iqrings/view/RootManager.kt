package com.katysh.iqrings.view

import android.widget.ImageView
import android.widget.RelativeLayout

class RootManager(
    private var root: RelativeLayout
) {

    fun placeOnScreen(imageView: ImageView,  x: Int, y: Int, w: Int, h: Int){
        val params = RelativeLayout.LayoutParams(w, h)
        params.leftMargin = x
        params.topMargin = y

        root.addView(imageView, params)
    }
}