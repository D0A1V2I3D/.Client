package top.davidon.ironware.modules.render

import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory

class Fullbright : Module("FullBright", ModuleCategory.RENDER) {
    private var gamma = 0F

    override fun onEnable() {
        gamma = mc.gameSettings.saturation
        mc.gameSettings.saturation = 100F
    }

    override fun onDisable() {
        mc.gameSettings.saturation = gamma
    }
}