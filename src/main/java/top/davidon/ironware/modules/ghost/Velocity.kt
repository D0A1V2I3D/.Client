package top.davidon.ironware.modules.ghost

import com.darkmagician6.eventapi.EventTarget
import org.lwjgl.input.Keyboard
import top.davidon.ironware.events.EventPlayerUpdate
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.settings.settings.NumberRangeSetting

class Velocity : Module("Velocity", ModuleCategory.GHOST) {
    var horizontal = NumberRangeSetting("Horizontal", 0, 75.0, 0.0, 100.0, 1.0)
    var vertical = NumberRangeSetting("Vertical", 1, 100.0, 0.0, 100.0, 1.0)
    var chance = NumberRangeSetting("Chance", 2, 100.0, 0.0, 100.0, 1.0)
    var onlyTarget = BooleanSetting("Only while targeting", 3, false)
    var holds = BooleanSetting("Disable while holding S", 4, false)

    init {
        settings.addSetting(horizontal)
        settings.addSetting(vertical)
        settings.addSetting(chance)
        settings.addSetting(onlyTarget)
        settings.addSetting(holds)
    }

    @EventTarget
    fun onEvent(e: EventPlayerUpdate) {
        if (enabled) {
            if (mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime === mc.thePlayer.maxHurtTime) {
                if (onlyTarget.get() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                    return
                }
                if (holds.get() && Keyboard.isKeyDown(mc.gameSettings.keyBindRight.keyCode)) {
                    return
                }
                if (chance.get() != 100.0) {
                    val ch = Math.random()
                    if (ch >= chance.get() / 100.0) {
                        return
                    }
                }
                if (horizontal.get() != 100.0) {
                    mc.thePlayer.motionX *= horizontal.get() / 100.0
                    mc.thePlayer.motionZ *= horizontal.get() / 100.0
                }
                if (vertical.get() != 100.0) {
                    mc.thePlayer.motionY *= vertical.get() / 100.0
                }
            }
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}