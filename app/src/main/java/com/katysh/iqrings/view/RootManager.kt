package com.katysh.iqrings.view

import android.widget.ImageView
import android.widget.RelativeLayout
import com.katysh.iqrings.model.FixedDetail
import com.katysh.iqrings.model.IntXY
import com.katysh.iqrings.model.MotileDetail

class RootManager(
    private var root: RelativeLayout
) {

    fun placeFixedDetail(detail: FixedDetail) {
        placeOnScreen(detail.compositeImage!!, IntXY(detail.hole.centerX, detail.hole.centerY))
    }

    fun placeInGrid(detail: MotileDetail) {
        placeOnScreen(detail.compositeImage!!, detail.detailGridXY)
    }

    fun placeOnScreen(imageView: ImageView, x: Int, y: Int, w: Int, h: Int) {
        val params = RelativeLayout.LayoutParams(w, h)
        params.leftMargin = x
        params.topMargin = y

        root.addView(imageView, params)
    }

    private fun placeOnScreen(compositeImage: CompositeImage, xy: IntXY) {
        for (part in compositeImage.parts) {
            placeOnScreen(
                part.imageView,
                xy.x + part.x,
                xy.y + part.y,
                part.w,
                part.h
            )
        }
    }

    fun remove(ci: CompositeImage?) {
        if (ci == null)
            return
        for (part in ci.parts) {
            root.removeView(part.imageView)
        }
    }
}