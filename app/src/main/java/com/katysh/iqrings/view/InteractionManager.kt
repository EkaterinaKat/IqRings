package com.katysh.iqrings.view

import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.Field
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.util.getSideHolePosition

class InteractionManager(
    val field: Field,
    sizeParams: GameSizeParams
) {

    private val holeActionRadius = sizeParams.holeDistance / 4

    fun reportMove(detail: Detail) {
        highlightHoles(detail)
    }

    private fun highlightHoles(detail: Detail) {
        val minX = detail.x - holeActionRadius
        val maxX = detail.x + holeActionRadius
        val minY = detail.y - holeActionRadius
        val maxY = detail.y + holeActionRadius

        var centerHole: Hole? = null

        for (hole in field.holes) {
            if (hole.centerX > minX
                && hole.centerX < maxX
                && hole.centerY > minY
                && hole.centerY < maxY
            ) {
                centerHole = hole
                break
            }
        }
        if (centerHole == null) {
            field.turnOffHighlightion()
            return
        }

        val leftHole =
            field.holesXyMap[getSideHolePosition(centerHole, detail.leftDirection!!)]
        val rightHole =
            field.holesXyMap[getSideHolePosition(centerHole, detail.rightDirection!!)]

        field.highlightHoles(centerHole, leftHole, rightHole)
    }

    companion object {
        val PI = 3.14
    }
}
