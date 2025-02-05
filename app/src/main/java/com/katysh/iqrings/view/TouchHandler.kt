package com.katysh.iqrings.view

import android.annotation.SuppressLint
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import com.katysh.iqrings.model.MotileDetail

@SuppressLint("ClickableViewAccessibility")
class TouchHandler {

    var controller: Controller? = null

    fun setTouchListener(detail: MotileDetail) {
        var dX = 0f
        var dY = 0f

        var clickCount = 0
        val doubleClickTimeout: Long = 300
        val handler = Handler()

        detail.compositeImage!!.parts.forEach {
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
                                    controller!!.reportDetailClick(detail)
                                }
                                clickCount = 0
                            }, doubleClickTimeout)
                        } else if (clickCount == 2) {
                            controller!!.reportDetailDoubleClick(detail)
                            clickCount = 0
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val x = event.rawX + dX
                        val y = event.rawY + dY
                        controller!!.reportActionMove(detail, it, x, y)
                        clickCount = 0
                    }

                    MotionEvent.ACTION_UP -> {
                        controller!!.reportActionUp(detail)
                        if (Math.abs(event.rawX + dX - iv.x) > 5 || Math.abs(event.rawY + dY - iv.y) > 5) {
                            clickCount = 0
                        }
                    }
                }
                true
            }
        }
    }
}