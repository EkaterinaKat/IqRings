package com.katysh.iqrings.view

import android.annotation.SuppressLint
import android.os.Handler
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

        var clickCount = 0
        val doubleClickTimeout: Long = 300
        val handler = Handler()

        compositeImage.ciParts.forEach {
            val iv = it.imageView

            iv.setOnTouchListener { view: View, event: MotionEvent ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = view.x - event.rawX
                        dY = view.y - event.rawY


                        // Обработка кликов
                        clickCount++

                        if (clickCount == 1) {
                            handler.postDelayed({
                                if (clickCount == 1) {
                                    Log.i("tag79631", "Одиночный клик")
                                }
                                clickCount = 0
                            }, doubleClickTimeout)
                        } else if (clickCount == 2) {
                            Log.i("tag79631", "Двойной клик")
                            clickCount = 0
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val x = event.rawX + dX
                        val y = event.rawY + dY
                        move(compositeImage, it, x, y)
                        clickCount = 0
                    }

                    MotionEvent.ACTION_UP -> {
                        if (Math.abs(event.rawX + dX - iv.x) > 5 || Math.abs(event.rawY + dY - iv.y) > 5) {
                            clickCount = 0
                        }
                    }
                }
                true
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