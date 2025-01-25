package com.katysh.iqrings.view

import android.content.Context
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.DetailCiData
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.ElementType
import com.katysh.iqrings.model.Field
import com.katysh.iqrings.model.FixedDetail
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.IntXY
import com.katysh.iqrings.model.MotileDetail
import com.katysh.iqrings.model.Solid
import com.katysh.iqrings.util.State
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
    private val rootManager: RootManager,
    private val field: Field
) {

    private val detailCiFactory = DetailCiFactory(context, gameSizeParams)
    private val touchHandler = TouchHandler(
        moveManager = moveManager,
        onClick = { rotateByClick(it) },
        onDoubleClick = { flipByClick(it) }
    )

    fun addMotileDetail(config: DetailConfig, gridRowColumn: IntXY) {
        val gridXY = gameSizeParams.getGridXyByGridRowColumn(gridRowColumn)
        val detail = MotileDetail(config, gridXY)
        setCi(detail)
        placeOnScreenInGrid(detail)
    }

    fun addFixedDetail(config: DetailConfig, state: State) {
        val detail = FixedDetail(config)
        detail.flipped = !state.isFrontSide
        detail.rotation = convertDirection(0, state.rotation, false)
        setCi(detail)
        val row = state.position[0]
        val column = state.position[1]
        val hole = field.getHoleByRowColumn(row, column)
        placeOnScreen(detail.compositeImage!!, IntXY(hole.centerX, hole.centerY))
    }

    private fun placeOnScreenInGrid(detail: MotileDetail) {
        placeOnScreen(detail.compositeImage!!, detail.detailGridXY)
    }

    //todo это не должно разве быть где-то в MoveEngine?
    private fun placeOnScreen(compositeImage: CompositeImage, xy: IntXY) {
        for (part in compositeImage.parts) {
            rootManager.placeOnScreen(
                part.imageView,
                xy.x + part.x,
                xy.y + part.y,
                part.w,
                part.h
            )
        }
    }

    private fun rotateByClick(detail: MotileDetail) {
        detail.rotation = convertDirection(detail.rotation, 1, false)
        onPositionChange(detail)
    }

    private fun flipByClick(detail: MotileDetail) {
        detail.flipped = !detail.flipped
        onPositionChange(detail)
    }

    private fun onPositionChange(detail: MotileDetail) {
        rootManager.remove(detail.compositeImage)
        setCi(detail)
        placeOnScreenInGrid(detail)
    }

    private fun setCi(detail: Detail) {
        val ciData = getCiData(detail)
        val ci = detailCiFactory.createDetailCi(ciData, detail.imageSet)
        detail.compositeImage = ci
        if (detail is MotileDetail)
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