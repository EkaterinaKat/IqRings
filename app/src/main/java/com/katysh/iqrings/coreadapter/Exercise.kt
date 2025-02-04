package com.katysh.iqrings.coreadapter

import android.content.Context
import com.katysh.iqrings.util.readAssetFile

class Exercise(
    context: Context,
    configAssetName: String
) {

    private var nativePtr: Long = 0

    val configStr: String

    init {
        System.loadLibrary("NativeRingsEngine")
        configStr = readAssetFile(context, configAssetName)
        nativePtr = createExercise(configStr)
    }

    @Throws(Throwable::class)
    protected fun finalize() {
        destroyExercise(nativePtr)
    }

    private external fun createExercise(configStr: String): Long
    private external fun destroyExercise(ptr: Long)
    private external fun isDetailFits(
        ptr: Long,
        detailNumber: Short,
        row: Short,
        column: Short,
        rotation: Short,
        side: Boolean
    ): Boolean

    private external fun insertDetail(
        ptr: Long,
        detailNumber: Short,
        row: Short,
        column: Short,
        rotation: Short,
        side: Boolean
    )

    private external fun removeDetail(ptr: Long, detailNumber: Short)
    private external fun isCompleted(ptr: Long): Boolean

    fun isDetailFits(
        detailNumber: Int,
        row: Int,
        column: Int,
        rotation: Int,
        side: Boolean
    ): Boolean {
        return isDetailFits(
            nativePtr,
            detailNumber.toShort(),
            row.toShort(),
            column.toShort(),
            rotation.toShort(),
            side
        )
    }

    fun insertDetail(
        detailNumber: Int,
        row: Int,
        column: Int,
        rotation: Int,
        side: Boolean
    ) {
        insertDetail(
            nativePtr,
            detailNumber.toShort(),
            row.toShort(),
            column.toShort(),
            rotation.toShort(),
            side
        )
    }

    fun removeDetail(detailNumber: Int) {
        removeDetail(nativePtr, detailNumber.toShort())
    }

    fun isCompleted(): Boolean {
        return isCompleted(nativePtr)
    }
}
