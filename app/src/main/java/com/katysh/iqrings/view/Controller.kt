package com.katysh.iqrings.view

import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail

class Controller(
    private val gameProgressManager: GameProgressManager,
    private val rootManager: RootManager,
    private val detailManager: DetailManager,
    private val moveManager: MoveManager,
    private val interactionManager: InteractionManager
) {

    fun reportDetailInsertion(detail: MotileDetail, hole: Hole) {
        gameProgressManager.insertDetail(detail, hole)
    }

    fun reportDetailReturnsToItsPlaceInGrid(detail: MotileDetail) {
        gameProgressManager.remove(detail)
    }

    fun reportDetailChangedCi(detail: MotileDetail, oldCi: CompositeImage) {
        rootManager.remove(oldCi)
        rootManager.placeInGrid(detail)
    }

    fun reportDetailClick(detail: MotileDetail) {
        detailManager.rotate(detail)
    }

    fun reportDetailDoubleClick(detail: MotileDetail) {
        detailManager.flip(detail)
    }

    fun reportActionUp(detail: MotileDetail) {
        moveManager.onReleaseDetail(detail)
    }

    fun reportActionMove(detail: MotileDetail, draggedPart: CiPart, dpnX: Float, dpnY: Float) {
        moveManager.move(detail, draggedPart, dpnX, dpnY)
        interactionManager.handleMovingDetailAndFieldInteraction(detail)
    }
}