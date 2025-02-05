package com.katysh.iqrings.view

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.katysh.iqrings.model.MotileDetail
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
class TouchHandler {

    var controller: Controller? = null

    fun getOnTouchListener(detail: MotileDetail, ciPart: CiPart): OnTouchListener {
        var dX = 0f
        var dY = 0f
        var downRawX = 0f
        var downRawY = 0f

        var clickCount = 0
        val doubleClickTimeout: Long = 300
        val handler = Handler()

        return OnTouchListener { view: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downRawX = event.rawX
                    downRawY = event.rawY
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
//                        Log.i("tag79879", "ACTION_DOWN view.x " + view.x)
//                        Log.i("tag79879", "ACTION_DOWN event.rawX " + event.rawX)
//                        Log.i("tag79879", "ACTION_DOWN dX $dX")

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
                    controller!!.reportActionMove(detail, ciPart, x, y)
                    clickCount = 0
                }

                MotionEvent.ACTION_UP -> {
//                        Log.i("tag79879", "ACTION_UP view.x " + view.x)
//                        Log.i("tag79879", "ACTION_UP event.rawX " + event.rawX)
//                        Log.i("tag79879", "ACTION_UP dX $dX")
//                        Log.i("tag79879", "ACTION_UP res " + abs(event.rawX + dX - view.x))

                    Log.i("tag79879", "ACTION_UP resX " + abs(event.rawX - downRawX))
                    Log.i("tag79879", "ACTION_UP resY " + abs(event.rawY - downRawY))

                    if (abs(event.rawX - downRawX) > MOVEMENT_SIGNIFICANCE_THRESHOLD
                        || abs(event.rawY - downRawY) > MOVEMENT_SIGNIFICANCE_THRESHOLD
                    ) {

                        Log.i("tag79879", "thereWasSignificantMovement")
                        controller!!.reportActionUp(detail)
                        clickCount = 0
                    } else {
                        Log.i("tag79879", "no")
                    }
                }
            }
            true
        }
    }

    companion object {
        const val MOVEMENT_SIGNIFICANCE_THRESHOLD = 10
    }
}