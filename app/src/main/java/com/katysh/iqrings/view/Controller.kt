package com.katysh.iqrings.view

import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail

class Controller(
    private val gameProgressManager: GameProgressManager
) {

    fun reportDetailInsertion(detail: MotileDetail, hole: Hole) {
        gameProgressManager.insertDetail(detail, hole)
    }

}