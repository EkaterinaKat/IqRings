package com.katysh.iqrings.model

import com.katysh.iqrings.view.CompositeImage

class Detail(
    val configuration: DetailConfig,
    var x: Int,
    var y: Int
) {

    var rotation: Int = 0
    var flipped: Boolean = false
    lateinit var compositeImage: CompositeImage
}
