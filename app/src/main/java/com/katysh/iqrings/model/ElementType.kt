package com.katysh.iqrings.model

sealed class ElementType

data object Ball : ElementType()
data object Solid : ElementType()
class Holey(val holes: List<Int>) : ElementType()