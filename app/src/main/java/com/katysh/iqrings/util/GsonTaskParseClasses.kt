package com.katysh.iqrings.util

data class State(
    val position: List<Int>,
    val rotation: Int,
    val isFrontSide: Boolean
)

data class InstalledDetail(
    val name: String,
    val state: State
)

data class GsonTaskConfig(
    val initial: List<Int>,
    val details: List<InstalledDetail>
)