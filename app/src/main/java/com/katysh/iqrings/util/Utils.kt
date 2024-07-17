package com.katysh.iqrings.util

import android.content.Context
import android.widget.ImageView
import com.google.gson.Gson
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.model.Hole
import com.katysh.iqrings.model.IntXY
import com.katysh.iqrings.view.CompositeImage
import java.io.InputStream
import kotlin.math.sign

fun readAssetFile(context: Context, fileName: String): String {
    val assetManager = context.assets
    val inputStream: InputStream = assetManager.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}

fun getImageView(context: Context, resId: Int): ImageView {
    val iv = ImageView(context)
    iv.setImageResource(resId)
    return iv
}

fun convertDirection(direction: Int, rotation: Int, flip: Boolean): Int {
    if (direction !in 0..5) {
        throw RuntimeException("Недопустимое значение числа: $direction")
    }

    val flipFactor = if (flip) -1 else 1
    return (direction * flipFactor + rotation + 12) % 6
}

fun getSideHolePosition(centerHole: Hole, direction: Int): IntXY {
    val d = centerHole.position.y % 2
    val r1 = (6 + direction + (1 + 3 * d)) % 6

    val y = centerHole.position.y - (if (direction > 0) sgn(direction - 3) else 0)
    val x =
        centerHole.position.x - sign(d * 1.0 - 0.5) * ((if (r1 < 3) r1 % 2 else 0) - (if (r1 >= 3) 1 else 0))
    return IntXY(x.toInt(), y)
}

fun sgn(x: Int): Int {
    return when {
        x > 0 -> 1
        x == 0 -> 0
        else -> -1
    }
}

fun getDetailGridXyByGridXy(gridXY: IntXY, image: CompositeImage): IntXY {
    val x = gridXY.x - ((image.rightBound + image.leftBound) / 2)
    val y = gridXY.y - ((image.bottomBound + image.topBound) / 2)
    return IntXY(x, y)
}

fun getOriginallyInstalledDetails(exercise: Exercise): List<InstalledDetail> {
    val gson = Gson()
    val response = gson.fromJson(exercise.configStr, GsonTaskConfig::class.java)
    return response.details.filter { response.initial.contains(it.name.toInt()) }
}

