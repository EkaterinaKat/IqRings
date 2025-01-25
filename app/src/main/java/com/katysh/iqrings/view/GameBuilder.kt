package com.katysh.iqrings.view

import android.content.Context
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.coreadapter.getConfigByName
import com.katysh.iqrings.coreadapter.getMotileDetails
import com.katysh.iqrings.model.ScreenBounds
import com.katysh.iqrings.util.getFixedDetails
import com.katysh.iqrings.util.getRowColumnByIndex

class GameBuilder(
    context: Context,
    root: RelativeLayout,
    private val exercise: Exercise
) {
    private val screenScale = ScreenScale(context)
    private val gameSizeParams = GameSizeParams(screenScale)
    private val rootManager = RootManager(root)

    private val field = FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()
    private val interactionManager = InteractionManager(field, gameSizeParams, exercise)
    private val moveManager =
        MoveManager(ScreenBounds(screenScale), interactionManager)
    private val detailManager =
        DetailManager(context, gameSizeParams, moveManager, rootManager, field)

    fun startGame() {

        val fixedDetails = getFixedDetails(exercise)

        fixedDetails.forEach {
            detailManager.addFixedDetail(getConfigByName(it.name), it.state)
        }

        val motileConfigs = getMotileDetails(fixedDetails.map { it.name })
        if (motileConfigs.size > 6) {
            throw RuntimeException("по техническим причинам мы не можем разместить больше 6 подвижных фигур")
        }

        for ((index, config) in motileConfigs.withIndex()) {
            detailManager.addMotileDetail(config, getRowColumnByIndex(index, 3))
        }
    }
}