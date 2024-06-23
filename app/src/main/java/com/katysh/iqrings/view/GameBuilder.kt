package com.katysh.iqrings.view

import android.content.Context
import android.widget.RelativeLayout
import com.katysh.iqrings.coreadapter.detailConfig0

class GameBuilder(
    private val context: Context,
    root: RelativeLayout
) {
    private val screenScale = ScreenScale(context)
    private val gameSizeParams = GameSizeParams(screenScale)
    private val rootManager = RootManager(root)
    private val detailManager = DetailManager(context, screenScale, gameSizeParams, rootManager)

    fun startGame() {
        FieldCreator(gameSizeParams, rootManager, context).createAndDrawField()

        detailManager.addDetail(detailConfig0)
    }
}