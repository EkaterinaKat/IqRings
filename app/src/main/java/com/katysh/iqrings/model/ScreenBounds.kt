package com.katysh.iqrings.model

import com.katysh.iqrings.view.ScreenScale

class ScreenBounds(
    screenScale: ScreenScale
) {
    val top = 0
    val bottom = screenScale.sh
    val left = 0
    val right = screenScale.sw
}