package com.katysh.iqrings.model

import com.katysh.iqrings.util.getDetailGridXyByGridXy

/**
 * x, y - координаты центра центрального элемента детали, меняются при перемещении фигуры
 * detailGridXY - значения x, y когда фигура стоит на своём месте в сетке
 */
class MotileDetail(
    configuration: DetailConfig,
    private val gridXY: IntXY
) : Detail(configuration, ImageSet.SET_1) {

    var x: Int = gridXY.x
    var y: Int = gridXY.y

    lateinit var detailGridXY: IntXY

    private fun updateDetailGridXY() {
        detailGridXY = getDetailGridXyByGridXy(gridXY, compositeImage!!)
    }

    override fun onCiUpdate() {
        updateDetailGridXY()
    }
}
