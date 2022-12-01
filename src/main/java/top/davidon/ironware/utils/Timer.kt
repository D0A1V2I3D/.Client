package top.davidon.ironware.utils

import net.minecraft.util.MathHelper

class Timer {
    var lastMS: Long = 0
    var previousTime: Long = 0

    init {
        lastMS = 0L
        previousTime = -1L
    }

    fun sleep(time: Long): Boolean {
        if (time() >= time) {
            reset()
            return true
        }
        return false
    }

    fun check(milliseconds: Float): Boolean {
        return System.currentTimeMillis() - previousTime >= milliseconds
    }

    fun delay(milliseconds: Double): Boolean {
        return MathHelper.clamp_float((getCurrentMS() - lastMS).toFloat(), 0f, milliseconds.toFloat()) >= milliseconds
    }

    fun reset() {
        previousTime = System.currentTimeMillis()
        lastMS = getCurrentMS()
    }

    fun time(): Long {
        return System.nanoTime() / 1000000L - lastMS
    }

    fun getCurrentMS(): Long {
        return System.nanoTime() / 1000000L
    }

    fun getLastDelay(): Double {
        return (getCurrentMS() - lastMS).toDouble()
    }

    fun hasTimeElapsed(time: Long, reset: Boolean): Boolean {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset == true) {
                reset()
            }
            return true
        }
        return false
    }
}