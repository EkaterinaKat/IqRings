package com.katysh.iqrings.model

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView

class Hole(
    val imageView: ImageView,
    val glowIv: ImageView,
    val centerX: Int,
    val centerY: Int,
    val columnRow: IntXY
) {

    fun highlight(active: Boolean) {
        if (active) {
            glowIv.visibility = VISIBLE
        } else {
            glowIv.visibility = GONE
        }
    }
}