package com.katysh.iqrings.view

import com.katysh.iqrings.model.MotileDetail
import com.katysh.iqrings.model.ScreenBounds

class MoveManager(
    screenBounds: ScreenBounds,
    private val interactionManager: InteractionManager
) {

    private val moveEngine = MoveEngine(screenBounds)
    var controller: Controller? = null

    //dpn - dragged part new coordinate
    fun onActionMove(
        detail: MotileDetail,
        draggedPart: CiPart,
        dpnX: Float,
        dpnY: Float
    ) {
        moveEngine.moveByDraggedPartCoords(detail, draggedPart, dpnX, dpnY)
        interactionManager.reportMove(detail)
    }

    fun onActionUp(detail: MotileDetail) {
        val hole = interactionManager.getHoleToInstallDetail(detail)
        if (hole != null) {
            moveEngine.moveByNewDetailCenterCoords(detail, hole.centerX, hole.centerY)
            controller!!.reportDetailInsertion(detail, hole)
        } else {
            moveEngine.moveDetailToInitPlaceInGrid(detail)
            controller!!.reportDetailReturnsToItsPlaceInGrid(detail)
        }
        interactionManager.reportActionUp()
    }
}