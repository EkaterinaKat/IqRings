package com.katysh.iqrings.view

import android.content.Context
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.MotileDetail
import com.katysh.iqrings.util.showAlertDialog

class GameProgressManager(
    private val exercise: Exercise,
    private val context: Context
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

    fun remove(detail: MotileDetail) {
        exercise.removeDetail(detail.configuration.id)
    }

    private fun checkIfGameFinished() {
        if (exercise.isCompleted()) {
            showAlertDialog(context, "Игра окончена!!!")
        }
    }
}