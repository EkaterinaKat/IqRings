package com.katysh.iqrings.model

class Field(
    val holes: List<Hole>
) {
    val holesXyMap: Map<IntXY, Hole> = holes.associateBy { it.columnRow }

    fun highlightHoles(vararg holesToHighlight: Hole?) {
        holes.forEach { it.highlight(false) }
        holesToHighlight.forEach { it?.highlight(true) }
    }

    fun turnOffHighlightion() {
        holes.forEach { it.highlight(false) }
    }

    fun getHoleByRowColumn(row: Int, column: Int): Hole {
        val filteredList = holes.filter { it.columnRow.x == column && it.columnRow.y == row }
        if (filteredList.size == 1) {
            return filteredList[0]
        } else {
            throw RuntimeException()
        }
    }
}