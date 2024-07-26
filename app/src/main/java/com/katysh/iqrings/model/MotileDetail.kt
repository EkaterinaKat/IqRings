package com.katysh.iqrings.model

import com.katysh.iqrings.util.getDetailGridXyByGridXy

/**
 * x, y - координаты центра центрального элемента детали, меняются при перемещении фигуры
 * detailGridXY - значения x, y когда фигура стоит на своём месте в сетке
 */
class MotileDetail(
    configuration: DetailConfig,
    private val gridXY: IntXY
) : Detail(configuration) {

    //эти значения должны устанавливаться только во время движения
    // и использоваться только после начала движения
    var x: Int? = null
    var y: Int? = null

    lateinit var detailGridXY: IntXY

    fun updateDetailGridXY() {
        detailGridXY = getDetailGridXyByGridXy(gridXY, compositeImage!!)
    }

    override fun onCiUpdate() {
        updateDetailGridXY()
    }
}
