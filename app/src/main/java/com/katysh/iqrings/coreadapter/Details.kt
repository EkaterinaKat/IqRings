package com.katysh.iqrings.coreadapter

import com.katysh.iqrings.model.Angle.ACUTE
import com.katysh.iqrings.model.Angle.OBTUSE
import com.katysh.iqrings.model.Angle.STRAIGHT
import com.katysh.iqrings.model.Ball
import com.katysh.iqrings.model.DetailConfig
import com.katysh.iqrings.model.Holey
import com.katysh.iqrings.model.Solid

val detailConfig0 = DetailConfig(
    ACUTE,
    Ball,
    Holey(listOf(4)),
    Holey(listOf(3))
)

val detailConfig1 = DetailConfig(
    STRAIGHT,
    Ball,
    Solid,
    Holey(listOf(2))
)

val detailConfig2 = DetailConfig(
    OBTUSE,
    Holey(listOf(1)),
    Solid,
    Ball
)

val detailConfig3 = DetailConfig(
    ACUTE,
    Holey(listOf(1)),
    Ball,
    Ball
)

val detailConfig4 = DetailConfig(
    STRAIGHT,
    Ball,
    Holey(listOf(1, 2)),
    Ball
)

val detailConfig5 = DetailConfig(
    OBTUSE,
    Holey(listOf(0)),
    Solid,
    Holey(listOf(1))
)

val detailConfig6 = DetailConfig(
    OBTUSE,
    Ball,
    Holey(listOf(5)),
    Ball
)

val detailConfig7 = DetailConfig(
    OBTUSE,
    Holey(listOf(4)),
    Solid,
    Ball
)

val detailConfig8 = DetailConfig(
    ACUTE,
    Holey(listOf(0)),
    Ball,
    Ball
)

val detailConfig9 = DetailConfig(
    OBTUSE,
    Holey(listOf(2)),
    Solid,
    Ball
)

val detailConfig10 = DetailConfig(
    ACUTE,
    Ball,
    Holey(listOf(3)),
    Ball
)

val detailConfig11 = DetailConfig(
    STRAIGHT,
    Holey(listOf(4)),
    Solid,
    Ball
)