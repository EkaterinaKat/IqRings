package com.katysh.iqrings.view

import com.katysh.iqrings.model.MotileDetail
import com.katysh.iqrings.model.ScreenBounds

class MoveEngine(
    private val bounds: ScreenBounds
) {

    fun moveByDraggedPartCoords(
        detail: MotileDetail,
        draggedPart: CiPart,
        dpnX: Float,
        dpnY: Float
    ) {
        val correctedDpnX = dpnX.coerceIn(
            bounds.left - draggedPart.leftBound!!,
            bounds.right - draggedPart.rightBound!!
        )
        val correctedDpnY = dpnY.coerceIn(
            bounds.top - draggedPart.topBound!!,
            bounds.bottom - draggedPart.bottomBound!!
        )

        val newDetailX = correctedDpnX - draggedPart.x
        val newDetailY = correctedDpnY - draggedPart.y

        moveByNewDetailCenterCoords(detail, newDetailX.toInt(), newDetailY.toInt())
    }

    fun moveDetailToInitPlaceInGrid(detail: MotileDetail) {
        moveByNewDetailCenterCoords(detail, detail.detailGridXY.x, detail.detailGridXY.y)
    }

    fun moveByNewDetailCenterCoords(detail: MotileDetail, newDetailX: Int, newDetailY: Int) {
        detail.x = newDetailX
        detail.y = newDetailY

        for (part in detail.compositeImage!!.parts) {
            val x = newDetailX + part.x
            val y = newDetailY + part.y

            //move
            part.imageView.animate()
                .x(x.toFloat()).y(y.toFloat())
                .setDuration(0)
                .start()
        }
    }
}