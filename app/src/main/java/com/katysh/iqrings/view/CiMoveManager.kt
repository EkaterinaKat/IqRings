package com.katysh.iqrings.view

import com.katysh.iqrings.util.ActionMoveListener

class CiMoveManager(
    private var topBound: Int,
    private var bottomBound: Int,
    private var leftBound: Int,
    private var rightBound: Int,
) : ActionMoveListener {

    //den - dragged part new coordinate
    //de - dragged part
    override fun execute(
        compositeImage: CompositeImage,
        draggedCiPart: CiPart,
        denX: Float,
        denY: Float
    ) {

        val deLeftBound = (compositeImage.leftBound - draggedCiPart.x).toFloat()
        val deRightBound = (compositeImage.rightBound - draggedCiPart.x).toFloat()
        val deTopBound = (compositeImage.topBound - draggedCiPart.y).toFloat()
        val deBottomBound = (compositeImage.bottomBound - draggedCiPart.y).toFloat()

        val correctedDenX = denX.coerceIn(leftBound - deLeftBound, rightBound - deRightBound)
        val correctedDenY = denY.coerceIn(topBound - deTopBound, bottomBound - deBottomBound)

        for (part in compositeImage.ciParts) {
            val x = correctedDenX - draggedCiPart.x + part.x
            val y = correctedDenY - draggedCiPart.y + part.y

            //move
            part.imageView.animate()
                .x(x).y(y)
                .setDuration(0)
                .start()
        }
    }
}