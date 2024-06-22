package com.katysh.iqrings.view

import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.detailConfig0
import com.katysh.iqrings.model.DetailConfig

class GameBuilder(
    private val context: Context,
    root: RelativeLayout
) {

    private val screenScale = ScreenScale(context)
    private val gameSizeParams = GameSizeParams(screenScale)
    private val rootManager = RootManager(root)
    private val moveManager = CiMoveManager(0, screenScale.sh, 0, screenScale.sw)
    private val detailFactory = DetailFactory(context, gameSizeParams)
    private val touchHandler = CiTouchHandler(
        moveListener = moveManager,
        onClick = { detailOnClick(it) },
        onDoubleClick = { detailOnDoubleClick(it) }
    )

    fun startGame() {
        FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()

        placeDetailOnScreen(detailConfig0)
    }

    private fun placeDetailOnScreen(detailConfig: DetailConfig) {
        val detail = detailFactory.createDetail(detailConfig)
        touchHandler.setTouchListener(detail.compositeImage)
        placeCiOnScreen(
            detail.compositeImage,
            screenScale.sw / 2,
            screenScale.sh / 2
        )
    }

    private fun placeCiOnScreen(compositeImage: CompositeImage, x: Int, y: Int) {
        for (part in compositeImage.ciParts) {
            rootManager.placeOnScreen(
                part.imageView,
                x + part.x,
                y + part.y,
                part.w,
                part.h
            )
        }
    }

    private fun detailOnClick(compositeImage: CompositeImage) {
        Log.i("tag79631", "Одиночный клик")
    }

    private fun detailOnDoubleClick(compositeImage: CompositeImage) {
        Log.i("tag79631", "Двойной клик")
    }
}