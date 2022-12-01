package top.davidon.ironware.settings

import top.davidon.ironware.modules.Module
import top.davidon.ironware.utils.vector.Vec4d
import top.davidon.ironware.utils.vector.Vec4i

open class Setting<T>(open var name: String, open var id: Int, open var type: SettingType, open var value: T) {
    var vec: Vec4i? = null

    open fun get(): T {
        return this.value
    }

    open fun set(value: T) {
        this.value = value
    }
}