package top.davidon.ironware.utils.vector

data class Vec4d(var x1: Double, var y1: Double, var x2: Double, var y2: Double) {
    fun get1Cord(): Vec2d {
        return Vec2d(x1, y1)
    }

    fun get2Cord(): Vec2d {
        return Vec2d(x2, y2)
    }
}