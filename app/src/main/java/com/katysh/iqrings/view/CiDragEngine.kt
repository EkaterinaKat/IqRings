package com.katysh.iqrings.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
class CiDragEngine(
    private var topBound: Int,
    private var bottomBound: Int,
    private var leftBound: Int,
    private var rightBound: Int,
) {

    fun makeDraggable(compositeImage: CompositeImage) {
        var dX = 0f
        var dY = 0f

        compositeImage.ciParts.forEach {
            val iv = it.imageView

            iv.setOnClickListener{
                Log.i("tag655229", "setOnClickListener")
            }
            iv.setOnLongClickListener{
                Log.i("tag655229", "setOnLongClickListener")
                true
            }

            iv.setOnTouchListener { view: View, event: MotionEvent ->
                var moved = false
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = view.x - event.rawX
                        dY = view.y - event.rawY
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val x = event.rawX + dX
                        val y = event.rawY + dY
                        move(compositeImage, it, x, y)
                        moved = true
                    }
                }
                moved
            }
        }
    }

    //den - dragged part new coordinate
    //de - dragged part
    fun move(compositeImage: CompositeImage, draggedCiPart: CiPart, denX: Float, denY: Float) {

        val deLeftBound = (compositeImage.leftBound - draggedCiPart.x).toFloat()
        val deRightBound = (compositeImage.rightBound - draggedCiPart.x).toFloat()
        val deTopBound = (compositeImage.topBound - draggedCiPart.y).toFloat()
        val deBottomBound = (compositeImage.bottomBound - draggedCiPart.y).toFloat()

        val correctedDenX = denX.coerceIn(leftBound-deLeftBound, rightBound-deRightBound)
        val correctedDenY = denY.coerceIn(topBound-deTopBound, bottomBound-deBottomBound)

        for (part in compositeImage.ciParts) {
            val x = correctedDenX - draggedCiPart.x + part.x
            val y = correctedDenY - draggedCiPart.y + part.y

            //move
            part.imageView.animate()
                .x(x).y(y)
                .setDuration(0)
                .start()
        }
    }
}