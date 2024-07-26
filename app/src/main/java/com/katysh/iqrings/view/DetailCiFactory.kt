package com.katysh.iqrings.view

import android.content.Context
import android.widget.ImageView
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.DetailCiData
import com.katysh.iqrings.model.Element
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.ImageSet
import com.katysh.iqrings.model.Solid
import com.katysh.iqrings.util.getImageView

class DetailCiFactory(
    private val context: Context,
    gsm: GameSizeParams
) {

    private val elementDistance = gsm.holeDistance.toInt()
    private val rowDistance = gsm.rowDistance.toInt()
    private val ballSize = gsm.ballElementSize.toInt()
    private val halfBallSize = ballSize / 2
    private val ringSize = gsm.ringElementSize.toInt()
    private val halfRingSize = ringSize / 2
    private val brBeamLength = gsm.brBeamLength.toInt()
    private val rrBeamLength = gsm.rrBeamLength.toInt()
    private val bbBeamLength = gsm.bbBeamLength.toInt()
    private val beamWidth = gsm.beamWidth.toInt()

    fun createDetailCi(data: DetailCiData, imageSet: ImageSet): CompositeImage {
        val parts = mutableListOf<CiPart>()

        val centerCenterCoords = Coordinates(0, 0)
        val rightCenterCoords = getElementCoordinates(data.rightDirection)
        val leftCenterCoords = getElementCoordinates(data.leftDirection)

        val leftElement = Element(data.left, leftCenterCoords)
        val centerElement = Element(data.center, centerCenterCoords)
        val rightElement = Element(data.right, rightCenterCoords)

        parts.add(getBeamPart(centerElement, leftElement, imageSet))
        parts.add(getBeamPart(rightElement, centerElement, imageSet))

        parts.add(getElementPart(leftElement, imageSet))
        parts.add(getElementPart(centerElement, imageSet))
        parts.add(getElementPart(rightElement, imageSet))

        return CompositeImage(parts)
    }

    private fun getBeamPart(element1: Element, element2: Element, imageSet: ImageSet): CiPart {
        val imageView = getImageView(context, imageSet.beam)
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        val firstElement = if (element1.center.x < element2.center.x) element1 else element2
        val secondElement = if (element1.center.x > element2.center.x) element1 else element2

        val beamLength = getBeamLength(element1, element2)

        val firstElementSize = if (firstElement.type is Ball) ballSize else ringSize

        val x = firstElement.center.x + firstElementSize / 2
        val y = firstElement.center.y - beamWidth / 2

        if (element1.center.y != element2.center.y) {
            imageView.pivotX = (firstElementSize/ 2 * -1).toFloat()
            imageView.pivotY = beamWidth / 2.toFloat()
            imageView.rotation = if (secondElement.center.y > firstElement.center.y) 60f else 300f
        }

        return CiPart(imageView, x, y, beamLength, beamWidth)
    }

    private fun getBeamLength(element1: Element, element2: Element): Int {
        return if (element1.type is Ball) {
            if (element2.type is Ball) {
                bbBeamLength
            } else {
                brBeamLength
            }
        } else {
            if (element2.type is Ball) {
                brBeamLength
            } else {
                rrBeamLength
            }
        }
    }

    private fun getElementCoordinates(direction: Int): Coordinates {
        return when (direction) {
            1 -> Coordinates(elementDistance / 2, rowDistance)

            2 -> Coordinates(-elementDistance / 2, rowDistance)

            3 -> Coordinates(-elementDistance, 0)

            4 -> Coordinates(-elementDistance / 2, -rowDistance)

            5 -> Coordinates(elementDistance / 2, -rowDistance)

            0 -> Coordinates(elementDistance, 0)

            else -> throw RuntimeException()
        }
    }

    private fun getElementPart(element: Element, imageSet: ImageSet): CiPart {
        return when (element.type) {
            is Ball -> getBallPart(element.center, imageSet)
            is Holey -> getHoleyPart(element.center, element.type.holes, imageSet)
            is Solid -> getSolidPart(element.center, imageSet)
        }
    }

    private fun getBallPart(centerCoordinates: Coordinates, imageSet: ImageSet): CiPart {
        return CiPart(
            getImageView(context, imageSet.ball),
            centerCoordinates.x - halfBallSize,
            centerCoordinates.y - halfBallSize,
            ballSize,
            ballSize
        )
    }

    private fun getSolidPart(centerCoordinates: Coordinates, imageSet: ImageSet): CiPart {
        return CiPart(
            getImageView(context, imageSet.solid),
            centerCoordinates.x - halfRingSize,
            centerCoordinates.y - halfRingSize,
            ringSize,
            ringSize
        )
    }

    private fun getHoleyPart(
        centerCoordinates: Coordinates,
        holes: List<Int>,
        imageSet: ImageSet
    ): CiPart {

        val imageView = when (holes.size) {
            1 -> {
                val iv = getImageView(context, imageSet.holey1)
                val rotationAngle = holes[0] * 60
                iv.rotation = rotationAngle.toFloat()
                iv
            }

            2 -> {
                val iv = getImageView(context, imageSet.holey2)
                if (holes[0] + 1 != holes[1]) {
                    throw RuntimeException()
                }
                val rotationAngle = holes[0] * 60
                iv.rotation = rotationAngle.toFloat()
                iv
            }

            else -> throw RuntimeException()
        }

        return CiPart(
            imageView,
            centerCoordinates.x - halfRingSize,
            centerCoordinates.y - halfRingSize,
            ringSize,
            ringSize
        )
    }
}