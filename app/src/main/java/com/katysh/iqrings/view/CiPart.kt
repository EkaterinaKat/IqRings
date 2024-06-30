package com.katysh.iqrings.view

import android.widget.ImageView

data class CiPart(
    val imageView: ImageView,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
) {
    var topBound: Float? = null
    var bottomBound: Float? = null
    var leftBound: Float? = null
    var rightBound: Float? = null
}