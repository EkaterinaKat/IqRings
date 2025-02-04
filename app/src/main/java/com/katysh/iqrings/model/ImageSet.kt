package com.katysh.iqrings.model

import com.katysh.iqrings.R

enum class ImageSet(
    val beam: Int,
    val ball: Int,
    val holey1: Int,
    val holey2: Int,
    val solid: Int
) {
    SHINY_PURPLE(
        R.mipmap.beam_1,
        R.mipmap.ball_1,
        R.mipmap.holey1_1,
        R.mipmap.holey2_1,
        R.mipmap.solid_1
    ),

    SHINY_GREY(
        R.mipmap.beam_2,
        R.mipmap.ball_2,
        R.mipmap.holey1_2,
        R.mipmap.holey2_2,
        R.mipmap.solid_2
    ),

    GREY(
        R.mipmap.grey_beam,
        R.mipmap.grey_ball,
        R.mipmap.grey_holey1,
        R.mipmap.holey2_2,
        R.mipmap.solid_2
    ),

    RED(
        R.mipmap.red_beam,
        R.mipmap.red_ball,
        R.mipmap.red_holey1,
        R.mipmap.holey2_2,
        R.mipmap.solid_2
    ),

    BLUE(
        R.mipmap.beam_2,
        R.mipmap.ball_2,
        R.mipmap.blue_holey1,
        R.mipmap.holey2_2,
        R.mipmap.solid_2
    ),

    GREEN(
        R.mipmap.beam_2,
        R.mipmap.ball_2,
        R.mipmap.green_holey1,
        R.mipmap.holey2_2,
        R.mipmap.solid_2
    ),

    YELLOW(
        R.mipmap.beam_2,
        R.mipmap.ball_2,
        R.mipmap.yellow_holey1,
        R.mipmap.holey2_2,
        R.mipmap.solid_2
    );

    companion object {
        fun getColorByIndex(index: Int): ImageSet {
            return listOf(RED, GREEN, BLUE, YELLOW)[index]
        }
    }

}