package com.katysh.iqrings.view

import com.katysh.iqrings.model.Detail

class CiMoveManager(
    private var topBound: Int,
    private var bottomBound: Int,
    private var leftBound: Int,
    private var rightBound: Int
) {

    //dpn - dragged part new coordinate
    fun execute(
        detail: Detail,
        draggedPart: CiPart,
        dpnX: Float,
        dpnY: Float
    ) {

        val correctedDpnX = dpnX.coerceIn(
            leftBound - draggedPart.leftBound!!,
            rightBound - draggedPart.rightBound!!
        )
        val correctedDpnY = dpnY.coerceIn(
            topBound - draggedPart.topBound!!,
            bottomBound - draggedPart.bottomBound!!
        )

        val newDetailX = correctedDpnX - draggedPart.x
        val newDetailY = correctedDpnY - draggedPart.y

        detail.x = newDetailX.toInt()
        detail.y = newDetailY.toInt()

        for (part in detail.compositeImage.parts) {
            val x = correctedDpnX - draggedPart.x + part.x
            val y = correctedDpnY - draggedPart.y + part.y

            //move
            part.imageView.animate()
                .x(x).y(y)
                .setDuration(0)
                .start()
        }
    }
}