package top.davidon.ironware.utils.vector

class Vec4i(var x1: Int, var y1: Int, var x2: Int, var y2: Int) {
    fun get1Cord(): Vec2i {
        return Vec2i(x1, y1)
    }

    fun get2Cord(): Vec2i {
        return Vec2i(x2, y2)
    }
}