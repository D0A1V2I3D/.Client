package top.davidon.ironware.modules.ghost

import com.darkmagician6.eventapi.EventTarget
import top.davidon.ironware.events.EventTick
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.NumberRangeSetting
import top.davidon.ironware.utils.Timer
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToInt

class Autoclicker : Module("AutoClicker", ModuleCategory.GHOST) {
    var timer = Timer()
    var min = NumberRangeSetting("Min", 0, 16.0, 0.0, 30.0, 0.1)
    var max = NumberRangeSetting("Max", 1, 22.0, 0.0, 30.0, 0.1)

    init {
        settings.addSetting(min)
        settings.addSetting(max)
    }

    @EventTarget
    fun onEvent(e: EventTick) {
        if (enabled) {
            if (mc.gameSettings.keyBindPickBlock.isKeyDown && !mc.thePlayer.isBlocking) {
                if (timer.delay(
                        (1000 / ThreadLocalRandom.current().nextInt(
                            min.get().roundToInt(),
                            (max.get() + 1).roundToInt()
                        )).toDouble()
                    )) {
                    mc.thePlayer.swingItem()
                    if (mc.objectMouseOver.entityHit != null) {
                        mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit)
                    }
                    timer.reset()
                }
            }
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}