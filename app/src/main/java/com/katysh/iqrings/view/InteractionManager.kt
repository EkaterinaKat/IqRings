package com.katysh.iqrings.view

import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.model.Field
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail
import com.katysh.iqrings.util.getSideHolePosition

class InteractionManager(
    private val field: Field,
    sizeParams: GameSizeParams,
    private val exercise: Exercise
) {

    var controller: Controller? = null

    private val holeActionRadius = sizeParams.holeDistance / 4

    fun onReleaseDetail(detail: MotileDetail) {
        val hole = getHoleToInstallDetail(detail)
        if (hole != null) {
            controller!!.reportDetailInsertion(detail, hole)
        } else {
            controller!!.reportDetailReturnsToItsPlaceInGrid(detail)
        }
        field.turnOffHighlightion()
    }

    private fun getHoleToInstallDetail(detail: MotileDetail): Hole? {
        for (hole in field.holes) {
            if (centerElementNearHole(detail, hole) && detailFitsInHole(detail, hole)) {
                return hole
            }
        }
        return null
    }

     fun handleMovingDetailAndFieldInteraction(detail: MotileDetail) {
         var holeSuitableForCenterElement: Hole? = null

         for (hole in field.holes) {
             if (centerElementNearHole(detail, hole) && detailFitsInHole(detail, hole)) {
                 holeSuitableForCenterElement = hole
                 break
             }
         }
         highlightHoles(detail, holeSuitableForCenterElement)
     }

    private fun highlightHoles(detail: MotileDetail, holeSuitableForCenterElement: Hole?) {
        if (holeSuitableForCenterElement == null) {
            field.turnOffHighlightion()
            return
        }

        val leftHole =
            field.holesXyMap[getSideHolePosition(
                holeSuitableForCenterElement,
                detail.leftDirection!!
            )]
        val rightHole =
            field.holesXyMap[getSideHolePosition(
                holeSuitableForCenterElement,
                detail.rightDirection!!
            )]

        field.highlightHoles(holeSuitableForCenterElement, leftHole, rightHole)
    }

    private fun centerElementNearHole(detail: MotileDetail, hole: Hole): Boolean {
        val minX = detail.x - holeActionRadius
        val maxX = detail.x + holeActionRadius
        val minY = detail.y - holeActionRadius
        val maxY = detail.y + holeActionRadius

        return hole.centerX > minX
                && hole.centerX < maxX
                && hole.centerY > minY
                && hole.centerY < maxY
    }

    private fun detailFitsInHole(detail: MotileDetail, hole: Hole): Boolean {
        return exercise.isDetailFits(
            detail.configuration.id,
            hole.columnRow.y,
            hole.columnRow.x,
            detail.rotation,
            !detail.flipped
        )
    }
}
