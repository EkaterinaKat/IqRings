package com.katysh.iqrings.view

import android.content.Context
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.coreadapter.detailConfig0
import com.katysh.iqrings.coreadapter.detailConfig1
import com.katysh.iqrings.coreadapter.detailConfig2
import com.katysh.iqrings.coreadapter.getConfigByName
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
            val config = getConfigByName(it.name)
            val state = it.state
        }

        detailManager.addDetail(detailConfig1, IntXY(0, 0))
        detailManager.addDetail(detailConfig0, IntXY(1, 0))
        detailManager.addDetail(detailConfig2, IntXY(2, 0))
    }
}