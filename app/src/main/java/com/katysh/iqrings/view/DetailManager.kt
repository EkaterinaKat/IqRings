package com.katysh.iqrings.view

import android.content.Context
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.DetailCiData
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.ElementType
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.IntXY
import com.katysh.iqrings.model.Solid
import com.katysh.iqrings.util.convertDirection

/**
 * gridRowColumn, gridXY и detailGridXY это всё разные сущности
 *
 * gridRowColumn - номера ряда и столбца.
 *
 * gridXY - координаты узла сетки в узлах которой должны располагаться детали, в этой координате
 * должен находится геометрический центр фигуры. Вычисляется из gridRowColumn
 * Для детали это значение константно.
 *
 * detailGridXY - координаты детали (то есть центра ее центральной фигуры) которые вычисляются из
 * параметров изображения и gridXY.
 * Для детали это значение перевычисляется каждый раз при флипе и повороте.
 */

class DetailManager(
    context: Context,
    private val gameSizeParams: GameSizeParams,
    private val moveManager: MoveManager,
    private val rootManager: RootManager
) {

    private val detailCiFactory = DetailCiFactory(context, gameSizeParams)
    private val touchHandler = TouchHandler(
        moveManager = moveManager,
        onClick = { rotate(it) },
        onDoubleClick = { flip(it) }
    )

    fun addDetail(config: DetailConfig, gridRowColumn: IntXY) {
        placeOnScreenInGrid(createDetail(config, gridRowColumn))
    }

    private fun createDetail(config: DetailConfig, gridRowColumn: IntXY): Detail {
        val gridXY = gameSizeParams.getGridXyByGridRowColumn(gridRowColumn)
        val detail = Detail(config, gridXY)
        setCi(detail)
        return detail
    }

    private fun placeOnScreenInGrid(detail: Detail) {
        for (part in detail.compositeImage.parts) {
            rootManager.placeOnScreen(
                part.imageView,
                detail.detailGridXY.x + part.x,
                detail.detailGridXY.y + part.y,
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
        placeOnScreenInGrid(detail)
    }

    private fun setCi(detail: Detail) {
        val ciData = getCiData(detail)
        val ci = detailCiFactory.createDetailCi(ciData)
        detail.compositeImage = ci
        detail.updatePosInGridXy()
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