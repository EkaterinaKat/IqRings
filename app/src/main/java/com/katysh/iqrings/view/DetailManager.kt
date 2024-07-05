package com.katysh.iqrings.view

import android.content.Context
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.DetailCiData
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.ElementType
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.Solid
import com.katysh.iqrings.util.convertDirection

class DetailManager(
    context: Context,
    gameSizeParams: GameSizeParams,
    moveManager: MoveManager,
    private val screenScale: ScreenScale,
    private val rootManager: RootManager
) {

    private val detailCiFactory = DetailCiFactory(context, gameSizeParams)
    private val touchHandler = TouchHandler(
        moveManager = moveManager,
        onClick = { rotate(it) },
        onDoubleClick = { flip(it) }
    )

    fun addDetail(config: DetailConfig) {
        placeOnScreen(createDetail(config))
    }

    private fun createDetail(config: DetailConfig): Detail {
        val detail = Detail(config, screenScale.sw / 2, screenScale.sh / 2)
        setCi(detail)
        return detail
    }

    private fun placeOnScreen(detail: Detail) {
        for (part in detail.compositeImage.parts) {
            rootManager.placeOnScreen(
                part.imageView,
                detail.x + part.x,
                detail.y + part.y,
                part.w,
                part.h
            )
        }
    }

    private fun rotate(detail: Detail) {
        detail.rotation = convertDirection(detail.rotation, 1, false)
        onPositionChange(detail)
    }

    private fun flip(detail: Detail) {
        detail.flipped = !detail.flipped
        onPositionChange(detail)
    }

    private fun onPositionChange(detail: Detail) {
        rootManager.remove(detail.compositeImage)
        setCi(detail)
        placeOnScreen(detail)
    }

    private fun setCi(detail: Detail) {
        val ciData = getCiData(detail)
        val ci = detailCiFactory.createDetailCi(ciData)
        detail.compositeImage = ci
        touchHandler.setTouchListener(detail)
    }

    private fun getCiData(detail: Detail): DetailCiData {
        return DetailCiData(
            left = getCiDataElement(detail.configuration.left, detail.rotation, detail.flipped),
            center = getCiDataElement(detail.configuration.center, detail.rotation, detail.flipped),
            right = getCiDataElement(detail.configuration.right, detail.rotation, detail.flipped),
            leftDirection = detail.leftDirection!!,
            rightDirection = detail.rightDirection!!
        )
    }

    private fun getCiDataElement(element: ElementType, rotation: Int, flip: Boolean): ElementType {
        return when (element) {
            is Ball -> Ball
            is Solid -> Solid
            is Holey -> {
                val holes = element.holes.map { convertDirection(it, rotation, flip) }
                Holey(holes)
            }
        }
    }

}