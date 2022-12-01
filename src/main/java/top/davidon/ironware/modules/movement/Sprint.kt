package top.davidon.ironware.modules.movement

import com.darkmagician6.eventapi.EventTarget
import net.minecraft.client.settings.KeyBinding
import top.davidon.ironware.events.EventMotion
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting

class Sprint : Module("Sprint", ModuleCategory.MOVEMENT) {
    var omni = BooleanSetting("Omni", 0, false)
    init {
        settings.addSetting(omni)
    }

    @EventTarget
    fun onEvent(e: EventMotion) {
        if (enabled) {
            if (!omni.get()) KeyBinding.setKeyBindState(
                mc.gameSettings.keyBindInventory.keyCode,
                true
            ) else if (omni.get()) {
                if (!mc.thePlayer.isUsingItem && !mc.thePlayer.isSneaking && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isMoving()) {
                    mc.thePlayer.setSprinting(true)
                }
            }
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
        if (!omni.get())
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindInventory.getKeyCode(), false)
        else if (omni.get())
            mc.thePlayer.setSprinting(false)
    }
}