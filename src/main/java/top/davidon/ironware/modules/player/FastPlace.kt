package top.davidon.ironware.modules.player

import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.NumberRangeSetting

class FastPlace : Module("FastPlace", ModuleCategory.PLAYER) {
    var delay = NumberRangeSetting("Delay", 0, 0.0, 0.0, 4.0, 1.0)

    init {
        settings.addSetting(delay)
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}