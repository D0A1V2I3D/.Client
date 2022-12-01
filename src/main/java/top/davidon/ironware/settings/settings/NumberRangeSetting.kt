package top.davidon.ironware.settings.settings

import top.davidon.ironware.modules.Module
import top.davidon.ironware.settings.Setting
import top.davidon.ironware.settings.SettingType

class NumberRangeSetting(override var name: String, override var id: Int, override var value: Double,
                         var min: Double, var max: Double, var step: Double) :
    Setting<Double>(name, id, SettingType.NUMBERRANGE, value) {
    override fun set(value: Double) {
        var value = value
        if (value > max) value = max
        else if (value < min) value = min
        super.set(value)
    }

    fun addOne() {
        set(value + step)
    }

    fun removeOne() {
        set(value - step)
    }
}