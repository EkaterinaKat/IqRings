package com.katysh.iqrings.view

import android.content.Context
import android.view.View.GONE
import android.widget.ImageView
import com.katysh.iqrings.R
import com.katysh.iqrings.model.Field
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.IntXY

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

        val shift = if (row % 2 == 0) 0.0 else distance / 2
        val centerX = gsm.leftmostHoleX + shift + (distance * column)
        val centerY = gsm.highestRowY + row * gsm.rowDistance

        val iv = ImageView(context)
        iv.setImageResource(R.mipmap.hole)

        val glow = ImageView(context)
        glow.setImageResource(R.mipmap.glow)
        glow.visibility = GONE

        return Hole(iv, glow, centerX.toInt(), centerY.toInt(), IntXY(column, row))
    }

    private fun drawField(field: Field) {
        val halfHoleSize = (gsm.holeSize / 2).toInt()
        val halfGlowSize = (gsm.glowSize / 2).toInt()

        for (hole in field.holes) {
            val imageX = hole.centerX - halfHoleSize
            val imageY = hole.centerY - halfHoleSize

            val glowX = hole.centerX - halfGlowSize
            val glowY = hole.centerY - halfGlowSize

            root.placeOnScreen(
                hole.imageView,
                imageX,
                imageY,
                gsm.holeSize.toInt(),
                gsm.holeSize.toInt()
            )
            root.placeOnScreen(
                hole.glowIv,
                glowX,
                glowY,
                gsm.glowSize.toInt(),
                gsm.glowSize.toInt()
            )
        }
    }
}