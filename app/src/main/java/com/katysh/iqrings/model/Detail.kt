package com.katysh.iqrings.model

import com.katysh.iqrings.util.convertDirection
import com.katysh.iqrings.view.CompositeImage

/**
 * x, y - координаты центра центрального элемента детали, меняются при перемещении фигуры
 */
class Detail(
    val configuration: DetailConfig,
    var x: Int,
    var y: Int
) {

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

    lateinit var compositeImage: CompositeImage

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
}
