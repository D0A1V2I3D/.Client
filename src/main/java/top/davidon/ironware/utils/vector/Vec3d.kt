package top.davidon.ironware.utils.vector

data class Vec3d @JvmOverloads constructor(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {

    fun copy(): Vec3d {
        return Vec3d(x, y, z)
    }
}