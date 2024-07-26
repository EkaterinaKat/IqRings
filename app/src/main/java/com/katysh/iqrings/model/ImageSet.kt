package com.katysh.iqrings.model

import com.katysh.iqrings.R

enum class ImageSet(
    val beam: Int,
    val ball: Int,
    val holey1: Int,
    val holey2: Int,
    val solid: Int
) {
    SET_1(R.mipmap.beam_1, R.mipmap.ball_1, R.mipmap.holey1_1, R.mipmap.holey2_1, R.mipmap.solid_1),
    SET_2(R.mipmap.beam_2, R.mipmap.ball_2, R.mipmap.holey1_2, R.mipmap.holey2_2, R.mipmap.solid_2)
}