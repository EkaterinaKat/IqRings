package com.katysh.iqrings.view

class CompositeImage(val ciParts: List<CiPart>) {

    var topBound = 0
    var bottomBound = 0
    var leftBound = 0
    var rightBound = 0

    init {
        val topBounds = mutableListOf<Int>()
        val bottomBounds = mutableListOf<Int>()
        val leftBounds = mutableListOf<Int>()
        val rightBounds = mutableListOf<Int>()

        for (part in ciParts) {
            topBounds.add(part.y)
            bottomBounds.add(part.y + part.h)
            leftBounds.add(part.x)
            rightBounds.add(part.x + part.w)
        }

        topBound = topBounds.min()
        bottomBound = bottomBounds.max()
        leftBound = leftBounds.min()
        rightBound = rightBounds.max()
    }
}

