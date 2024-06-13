package com.katysh.iqrings.view

import android.content.Context
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
    private val ciDragEngine = CiDragEngine(0, screenScale.sh, 0, screenScale.sw)
    private val detailFactory = DetailFactory(context, gameSizeParams)

    fun startGame() {
        FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()

        placeDetailOnScreen(detailConfig0)
//        placeDetailOnScreen(detail1)
//        placeDetailOnScreen(detail2)
//        placeDetailOnScreen(detail3)
//        placeDetailOnScreen(detail4)
    }

    private fun placeDetailOnScreen(detailConfig: DetailConfig){
        val detail = detailFactory.createDetail(detailConfig)
        ciDragEngine.makeDraggable(detail.compositeImage)
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
}