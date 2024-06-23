package com.katysh.iqrings.view

import android.content.Context
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.DetailCiData
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.ElementType
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.Solid

class DetailManager(
    private val context: Context,
    private val screenScale: ScreenScale,
    private val gameSizeParams: GameSizeParams,
    private val rootManager: RootManager
) {

    private val moveManager = CiMoveManager(0, screenScale.sh, 0, screenScale.sw)
    private val detailCiFactory = DetailCiFactory(context, gameSizeParams)
    private val touchHandler = TouchHandler(
        moveListener = moveManager,
        onClick = { rotate(it) },
        onDoubleClick = { flip(it) }
    )

    fun addDetail(config: DetailConfig) {
        placeDetailOnScreen(createDetail(config))
    }

    private fun createDetail(config: DetailConfig): Detail {
        val detail = Detail(config)
        setCi(detail)
        return detail
    }

    private fun placeDetailOnScreen(detail: Detail) {
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
        placeDetailOnScreen(detail)
    }

    private fun setCi(detail: Detail) {
        val ciData = getCiData(detail)
        val ci = detailCiFactory.createDetailCi(ciData)
        detail.compositeImage = ci
        touchHandler.setTouchListener(detail)
    }

    private fun convertDirection(direction: Int, rotation: Int, flip: Boolean): Int {
        if (direction !in 0..5) {
            throw RuntimeException("Недопустимое значение числа: $direction")
        }

        val flipFactor = if (flip) -1 else 1
        return (direction * flipFactor + rotation + 12) % 6
    }

    private fun getCiData(detail: Detail): DetailCiData {
        return DetailCiData(
            left = getCiDataElement(detail.configuration.left, detail.rotation, detail.flipped),
            center = getCiDataElement(detail.configuration.center, detail.rotation, detail.flipped),
            right = getCiDataElement(detail.configuration.right, detail.rotation, detail.flipped),
            leftDirection = convertDirection(0, detail.rotation, detail.flipped),
            rightDirection = convertDirection(
                detail.configuration.angle.rightElementDeviation,
                detail.rotation,
                detail.flipped
            )
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