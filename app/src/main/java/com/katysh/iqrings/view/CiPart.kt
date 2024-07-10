package com.katysh.iqrings.view

import android.widget.ImageView

data class CiPart(
    val imageView: ImageView,
    //xy части относительно центра CI
    val x: Int,
    val y: Int,
    //ширина высота
    val w: Int,
    val h: Int
) {
    //границы CI относительно xy части, нужно для того чтобы при движении каждый раз их не вычислять
    //во время корректировки на границы перемещения
    var topBound: Float? = null
    var bottomBound: Float? = null
    var leftBound: Float? = null
    var rightBound: Float? = null
}