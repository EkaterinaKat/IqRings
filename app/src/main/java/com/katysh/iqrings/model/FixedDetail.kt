package com.katysh.iqrings.model

class FixedDetail(
    configuration: DetailConfig,
    val hole: Hole //отверстие в которое установлен центральный элемент детали
) : Detail(configuration, ImageSet.SET_2) {

    override fun onCiUpdate() {
        //насин
    }
}