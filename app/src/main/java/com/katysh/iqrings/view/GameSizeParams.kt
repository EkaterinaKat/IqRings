package com.katysh.iqrings.view

import com.katysh.iqrings.model.IntXY
import kotlin.math.sqrt

class GameSizeParams(ss: ScreenScale) {

    val screenCenterY = ss.sh / 2
    val screenCenterX = ss.sw / 2

    val fieldCenterY = screenCenterY
    val fieldCenterX = screenCenterX

    val fieldWidth = ss.getWidth(0.8f)

    val holeDistance = fieldWidth / 5.5
    val holeSize = holeDistance * 0.7
    val glowSize = holeSize * 1.2
    val rowDistance = holeDistance * sqrt(3.0) / 2

    val highestRowY = fieldCenterY - 1.5 * rowDistance
    val leftmostHoleX = fieldCenterX - fieldWidth / 2

    val ballElementSize = holeSize * 0.7
    val ringElementSize = holeSize

    val beamWidth = holeDistance / 4
    val brBeamLength = (holeDistance - ballElementSize / 2 - ringElementSize / 2)
    val rrBeamLength = (holeDistance - ringElementSize)
    val bbBeamLength = (holeDistance - ballElementSize)

    //ключ - колонка и ряд, значение координаты
    private val positionsInGrid: MutableMap<IntXY, IntXY> = HashMap()
    private val rowsHeight = listOf(
        ss.sh / 5,
        ss.sh * 2 / 5,
    )

    init {
        val numOfColumns = 3
        val detailWidthSpace = ss.sw / numOfColumns

        for (row in rowsHeight.indices) {
            for (column in 0 until numOfColumns) {
                val x = detailWidthSpace / 2 + column * detailWidthSpace
                val y = rowsHeight[row]
                positionsInGrid[IntXY(column, row)] = IntXY(x, y)
            }
        }
    }

    fun getGridXyByGridRowColumn(gridRowColumn: IntXY): IntXY {
        return positionsInGrid[gridRowColumn]
            ?: throw RuntimeException("Coordinates for given detail row and column not found")
    }
}
