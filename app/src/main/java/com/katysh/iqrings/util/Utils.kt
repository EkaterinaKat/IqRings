package com.katysh.iqrings.util

import android.content.Context
import android.widget.ImageView
import java.io.InputStream

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