package com.katysh.iqrings.view

import android.content.Context
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.detailConfig0

class GameBuilder(
    context: Context,
    root: RelativeLayout
) {
    private val screenScale = ScreenScale(context)
    private val gameSizeParams = GameSizeParams(screenScale)
    private val rootManager = RootManager(root)

    private val field = FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()
    private val interactionManager = InteractionManager(field, gameSizeParams)
    private val moveManager =
        MoveManager(0, screenScale.sh, 0, screenScale.sw, interactionManager)
    private val detailManager =
        DetailManager(context, gameSizeParams, moveManager, screenScale, rootManager)

    fun startGame() {
        detailManager.addDetail(detailConfig0)
    }
}