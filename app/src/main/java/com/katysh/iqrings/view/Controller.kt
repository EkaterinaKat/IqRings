package com.katysh.iqrings.view

import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail

class Controller(
    private val gameProgressManager: GameProgressManager,
    private val rootManager: RootManager
) {

    fun reportDetailInsertion(detail: MotileDetail, hole: Hole) {
        gameProgressManager.insertDetail(detail, hole)
    }

    fun reportDetailChangedCi(detail: MotileDetail, oldCi: CompositeImage) {
        rootManager.remove(oldCi)
        rootManager.placeInGrid(detail)
    }

}