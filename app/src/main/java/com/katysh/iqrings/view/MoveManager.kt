package com.katysh.iqrings.view

import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail
import com.katysh.iqrings.model.ScreenBounds

class MoveManager(
    screenBounds: ScreenBounds,
    private val interactionManager: InteractionManager,
    private val exercise: Exercise
) {

    private val moveEngine = MoveEngine(screenBounds)

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
            insertDetail(detail, hole)
        } else {
            moveEngine.moveDetailToInitPlaceInGrid(detail)
        }
        interactionManager.reportActionUp()
    }

    private fun insertDetail(detail: MotileDetail, hole: Hole) {
        moveEngine.moveByNewDetailCenterCoords(detail, hole.centerX, hole.centerY)
        exercise.insertDetail(
            detail.configuration.id,
            hole.columnRow.y,
            hole.columnRow.x,
            detail.rotation,
            !detail.flipped
        )
        checkIfGameFinished()
    }

    //todo это вроде не здесь должно быть
    private fun checkIfGameFinished() {
        if (exercise.isCompleted()) {

        }

    }
}