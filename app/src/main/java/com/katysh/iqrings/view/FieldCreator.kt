package com.katysh.iqrings.view

import android.content.Context
import android.widget.ImageView
import com.katysh.iqrings.R
import com.katysh.iqrings.model.Field
import com.katysh.iqrings.model.Hole

class FieldCreator(
    private val gsm: GameSizeParams,
    private val root: RootManager,
    private val context: Context
) {

    fun createAndDrawField(): Field {
        val holes = mutableListOf<Hole>()
        for (row in 0..3) {
            for (column in 0..5) {
                holes.add(createHole(row, column))
            }
        }
        val field = Field(holes)
        drawField(field)
        return field
    }

    private fun createHole(row: Int, column: Int): Hole {
        val distance = gsm.holeDistance
        val halfHole = gsm.holeSize / 2

        val shift = if (row % 2 == 0) 0.0 else distance / 2
        val centerX = gsm.leftmostHoleX + shift + (distance * column)
        val imageX = centerX - halfHole
        val centerY = gsm.highestRowY + row * gsm.rowDistance
        val imageY = centerY - halfHole

        val iv = ImageView(context)
        iv.setImageResource(R.mipmap.hole)

        return Hole(iv, imageX.toInt(), imageY.toInt(), centerX.toInt(), centerY.toInt())
    }

    private fun drawField(field: Field) {
        for (hole in field.holes) {
            root.placeOnScreen(
                hole.imageView,
                hole.imageX,
                hole.imageY,
                gsm.holeSize.toInt(),
                gsm.holeSize.toInt()
            )
        }
    }
}