package top.davidon.ironware.utils

class Cords {
    private var x = 0
    private var y = 0
    private var z = 0

    constructor(x: Int, y: Int, z: Int) {
        this.x = x
        this.y = y
        this.z = z
    }

    constructor (x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}