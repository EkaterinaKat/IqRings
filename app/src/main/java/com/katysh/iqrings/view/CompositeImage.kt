package com.katysh.iqrings.view

class CompositeImage(val parts: List<CiPart>) {

    var topBound = 0
    var bottomBound = 0
    var leftBound = 0
    var rightBound = 0

    init {
        //вычисляем границы CompositeImage
        val topBounds = mutableListOf<Int>()
        val bottomBounds = mutableListOf<Int>()
        val leftBounds = mutableListOf<Int>()
        val rightBounds = mutableListOf<Int>()

        for (part in parts) {
            topBounds.add(part.y)
            bottomBounds.add(part.y + part.h)
            leftBounds.add(part.x)
            rightBounds.add(part.x + part.w)
        }

        topBound = topBounds.min()
        bottomBound = bottomBounds.max()
        leftBound = leftBounds.min()
        rightBound = rightBounds.max()

        //вычисляем границы частей
        for (part in parts) {
            part.leftBound = (leftBound - part.x).toFloat()
            part.rightBound = (rightBound - part.x).toFloat()
            part.topBound = (topBound - part.y).toFloat()
            part.bottomBound = (bottomBound - part.y).toFloat()
        }
    }
}

