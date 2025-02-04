package com.katysh.iqrings.view

import android.content.Context
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.coreadapter.getConfigByName
import com.katysh.iqrings.coreadapter.getMotileDetails
import com.katysh.iqrings.model.ImageSet
import com.katysh.iqrings.model.ScreenBounds
import com.katysh.iqrings.util.getFixedDetails
import com.katysh.iqrings.util.getRowColumnByIndex

class GameBuilder(
    context: Context,
    root: RelativeLayout
) {
    private val exercise = Exercise(context, "junior25.json")
    private val gameProgressManager = GameProgressManager(exercise)

    private val screenScale = ScreenScale(context)
    private val gameSizeParams = GameSizeParams(screenScale)
    private val rootManager = RootManager(root)

    private val field = FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()
    private val interactionManager = InteractionManager(field, gameSizeParams, exercise)
    private val moveManager =
        MoveManager(ScreenBounds(screenScale), interactionManager)
    private val detailManager =
        DetailManager(context, gameSizeParams, moveManager, field)

    private val controller = Controller(gameProgressManager, rootManager)

    fun startGame() {
        moveManager.controller = controller
        detailManager.controller = controller

        placeAllDetailsOnField()
    }

    private fun placeAllDetailsOnField() {
        val fixedDetails = getFixedDetails(exercise)

        fixedDetails.forEach {
            val detail = detailManager.getFixedDetail(getConfigByName(it.name), it.state)
            rootManager.placeFixedDetail(detail)
        }

        val motileConfigs = getMotileDetails(fixedDetails.map { it.name })
        if (motileConfigs.size > 6) {
            throw RuntimeException("по техническим причинам мы не можем разместить больше 6 подвижных фигур")
        }

        for (index in motileConfigs.indices) {
            val detail = detailManager.getMotileDetail(
                motileConfigs[index],
                getRowColumnByIndex(index, 3),
                ImageSet.getColorByIndex(index)
            )
            rootManager.placeInGrid(detail)
        }
    }
}