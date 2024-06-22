package com.katysh.iqrings.util

import com.katysh.iqrings.view.CiPart
import com.katysh.iqrings.view.CompositeImage

interface ActionMoveListener {
    fun execute(compositeImage: CompositeImage, draggedCiPart: CiPart, denX: Float, denY: Float)
}