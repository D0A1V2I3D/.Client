package top.davidon.ironware.utils.vector

data class Vec2i @JvmOverloads constructor(var x: Int = 0, var y: Int = 0) {

    fun copy(): Vec2i {
        return Vec2i(x, y)
    }
}