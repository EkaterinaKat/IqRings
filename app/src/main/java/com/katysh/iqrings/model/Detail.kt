package com.katysh.iqrings.model

import com.katysh.iqrings.util.convertDirection
import com.katysh.iqrings.view.CompositeImage

abstract class Detail(val configuration: DetailConfig) {

    var compositeImage: CompositeImage? = null
        set(value) {
            field = value
            onCiUpdate()
        }

    var rotation: Int = 0
        set(value) {
            field = value
            updateDirections()
        }
    var flipped: Boolean = false
        set(value) {
            field = value
            updateDirections()
        }

    var leftDirection: Int? = null
    var rightDirection: Int? = null

    init {
        updateDirections()
    }

    private fun updateDirections() {
        leftDirection = convertDirection(0, rotation, flipped)
        rightDirection = convertDirection(
            configuration.angle.rightElementDeviation,
            rotation,
            flipped
        )
    }

    abstract fun onCiUpdate()
}