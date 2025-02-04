package com.katysh.iqrings.view

import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail

class GameProgressManager(
    private val exercise: Exercise
) {

    fun insertDetail(detail: MotileDetail, hole: Hole) {
        exercise.insertDetail(
            detail.configuration.id,
            hole.columnRow.y,
            hole.columnRow.x,
            detail.rotation,
            !detail.flipped
        )
        checkIfGameFinished()
    }

    private fun checkIfGameFinished() {
        if (exercise.isCompleted()) {
            //todo
        }
    }
}