package com.katysh.iqrings.view

import android.content.Context
import android.widget.ImageView
import com.katysh.iqrings.R
import com.katysh.iqrings.model.Angle
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.Detail
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.Element
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.Solid
import com.katysh.iqrings.util.getImageView

class DetailFactory(
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

    fun createDetail(detailConfig: DetailConfig): Detail {
        val parts = mutableListOf<CiPart>()

        val centerCenterCoords = Coordinates(0, 0)
        val rightCenterCoords = getRightCoordinates(detailConfig.angle)
        val leftCenterCoords = Coordinates(elementDistance, 0)

        val leftElement = Element(detailConfig.left, leftCenterCoords)
        val centerElement = Element(detailConfig.center, centerCenterCoords)
        val rightElement = Element(detailConfig.right, rightCenterCoords)

        parts.add(getBeamPart(centerElement, leftElement))
        parts.add(getBeamPart(rightElement, centerElement))

        parts.add(getElementPart(leftElement))
        parts.add(getElementPart(centerElement))
        parts.add(getElementPart(rightElement))

        return Detail(
            detailConfig.angle,
            CompositeImage(parts),
            leftElement,
            centerElement,
            rightElement
        )
    }

    private fun getBeamPart(element1: Element, element2: Element): CiPart {
        val imageView = getImageView(context, R.mipmap.beam)
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

    private fun getRightCoordinates(angle: Angle): Coordinates {
        return when (angle) {
            Angle.ACUTE -> Coordinates(elementDistance / 2, rowDistance)

            Angle.OBTUSE -> Coordinates(-elementDistance / 2, rowDistance)

            Angle.STRAIGHT -> Coordinates(-elementDistance, 0)

        }
    }

    private fun getElementPart(element: Element): CiPart {
        return when (element.type) {
            is Ball -> getBallPart(element.center)
            is Holey -> getHoleyPart(element.center, element.type.holes)
            is Solid -> getSolidPart(element.center)
        }
    }

    private fun getBallPart(centerCoordinates: Coordinates): CiPart {
        return CiPart(
            getImageView(context, R.mipmap.ball),
            centerCoordinates.x - halfBallSize,
            centerCoordinates.y - halfBallSize,
            ballSize,
            ballSize
        )
    }

    private fun getSolidPart(centerCoordinates: Coordinates): CiPart {
        return CiPart(
            getImageView(context, R.mipmap.solid),
            centerCoordinates.x - halfRingSize,
            centerCoordinates.y - halfRingSize,
            ringSize,
            ringSize
        )
    }

    private fun getHoleyPart(centerCoordinates: Coordinates, holes: List<Int>): CiPart {

        val imageView = when (holes.size) {
            1 -> {
                val iv = getImageView(context, R.mipmap.holey1)
                val rotationAngle = holes[0] * 60
                iv.rotation = rotationAngle.toFloat()
                iv
            }

            2 -> {
                val iv = getImageView(context, R.mipmap.holey2)
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