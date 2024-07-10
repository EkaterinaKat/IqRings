package com.katysh.iqrings.model

import com.katysh.iqrings.util.convertDirection
import com.katysh.iqrings.util.getDetailGridXyByGridXy
import com.katysh.iqrings.view.CompositeImage

/**
 * x, y - координаты центра центрального элемента детали, меняются при перемещении фигуры
 */
class Detail(
    val configuration: DetailConfig,
    private val gridXY: IntXY
) {

    //эти значения должны устанавливаться только во время движения
    // и использоваться только после начала движения
    var x: Int? = null
    var y: Int? = null

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

    lateinit var detailGridXY: IntXY

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

    fun updatePosInGridXy() {
        detailGridXY = getDetailGridXyByGridXy(gridXY, compositeImage)
    }
}
