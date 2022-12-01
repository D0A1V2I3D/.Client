package top.davidon.ironware.modules.player

import com.darkmagician6.eventapi.EventTarget
import top.davidon.ironware.events.EventTick
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import kotlin.math.floor
import kotlin.math.round

class Catch: Module("Catch", ModuleCategory.PLAYER) {

    @EventTarget
    fun onEvent(e: EventTick) {
        if (enabled) {
            var findY = floor(mc.thePlayer.posY)
            var findX = round(mc.thePlayer.posX)
            var findZ = round(mc.thePlayer.posZ)
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}