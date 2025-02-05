package com.katysh.iqrings.view

import android.annotation.SuppressLint
import android.content.Context
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.DetailCiData
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.ElementType
import com.katysh.iqrings.model.Field
import com.katysh.iqrings.model.FixedDetail
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.ImageSet
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
 *
 * Position это у нас поворот и флип, а не координаты
 */

class DetailManager(
    context: Context,
    private val gameSizeParams: GameSizeParams,
    private val field: Field,
    private val touchHandler: TouchHandler
) {

    var controller: Controller? = null

    private val detailCiFactory = DetailCiFactory(context, gameSizeParams)

    fun getMotileDetail(
        config: DetailConfig,
        gridRowColumn: IntXY,
        imageSet: ImageSet
    ): MotileDetail {
        val gridXY = gameSizeParams.getGridXyByGridRowColumn(gridRowColumn)
        val detail = MotileDetail(config, imageSet, gridXY)
        setCi(detail)
        return detail
    }

    fun getFixedDetail(config: DetailConfig, state: State): FixedDetail {
        val row = state.position[0]
        val column = state.position[1]
        val hole = field.getHoleByRowColumn(row, column)
        val detail = FixedDetail(config, hole)
        detail.flipped = !state.isFrontSide
        detail.rotation = convertDirection(0, state.rotation, false)
        setCi(detail)
        return detail
    }

    fun rotate(detail: MotileDetail) {
        detail.rotation = convertDirection(detail.rotation, 1, false)
        onPositionChange(detail)
    }

    fun flip(detail: MotileDetail) {
        detail.flipped = !detail.flipped
        onPositionChange(detail)
    }

    private fun onPositionChange(detail: MotileDetail) {
        val oldCi = detail.compositeImage
        setCi(detail)

        controller!!.reportDetailChangedCi(detail, oldCi!!)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setCi(detail: Detail) {
        val ciData = getCiData(detail)
        val ci = detailCiFactory.createDetailCi(ciData, detail.imageSet)

        if (detail is MotileDetail) {
            ci.parts.forEach {
                it.imageView.setOnTouchListener(touchHandler.getOnTouchListener(detail, it))
            }
        }
        detail.compositeImage = ci
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

            else -> {
                throw RuntimeException()
            }
        }
    }

}