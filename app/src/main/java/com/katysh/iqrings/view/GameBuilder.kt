package com.katysh.iqrings.view

import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.coreadapter.detailConfig0
import com.katysh.iqrings.coreadapter.detailConfig1
import com.katysh.iqrings.coreadapter.detailConfig10
import com.katysh.iqrings.coreadapter.detailConfig11
import com.katysh.iqrings.coreadapter.detailConfig2
import com.katysh.iqrings.coreadapter.detailConfig3
import com.katysh.iqrings.coreadapter.detailConfig4
import com.katysh.iqrings.coreadapter.detailConfig5
import com.katysh.iqrings.coreadapter.detailConfig6
import com.katysh.iqrings.coreadapter.detailConfig7
import com.katysh.iqrings.coreadapter.detailConfig8
import com.katysh.iqrings.coreadapter.detailConfig9
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.IntXY
import com.katysh.iqrings.util.getOriginallyInstalledDetails

class GameBuilder(
    context: Context,
    root: RelativeLayout,
    private val exercise: Exercise
) {
    private val screenScale = ScreenScale(context)
    private val gameSizeParams = GameSizeParams(screenScale)
    private val rootManager = RootManager(root)

    private val field = FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()
    private val interactionManager = InteractionManager(field, gameSizeParams)
    private val moveManager =
        MoveManager(0, screenScale.sh, 0, screenScale.sw, interactionManager)
    private val detailManager =
        DetailManager(context, gameSizeParams, moveManager, rootManager)

    fun startGame() {

        val originallyInstalledDetails = getOriginallyInstalledDetails(exercise)

        originallyInstalledDetails.forEach {
            Log.i("tag468354", it.toString())
        }

        detailManager.addDetail(detailConfig1, IntXY(0, 0))
        detailManager.addDetail(detailConfig0, IntXY(1, 0))
        detailManager.addDetail(detailConfig2, IntXY(2, 0))
    }

    private val nameToConfigMap: Map<String, DetailConfig> = mapOf(
        "00" to detailConfig0,
        "01" to detailConfig1,
        "02" to detailConfig2,
        "03" to detailConfig3,
        "04" to detailConfig4,
        "05" to detailConfig5,
        "06" to detailConfig6,
        "07" to detailConfig7,
        "08" to detailConfig8,
        "09" to detailConfig9,
        "10" to detailConfig10,
        "11" to detailConfig11,
    )
}