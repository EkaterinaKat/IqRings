package com.katysh.iqrings.model

class Field(
    val holes: List<Hole>
) {
    val holesXyMap: Map<IntXY, Hole> = holes.associateBy { it.position }

    fun highlightHoles(vararg holesToHighlight: Hole?) {
        holes.forEach { it.highlight(false) }
        holesToHighlight.forEach { it?.highlight(true) }
    }

    fun turnOffHighlightion() {
        holes.forEach { it.highlight(false) }
    }
}