package top.davidon.ironware.modules.player

import com.darkmagician6.eventapi.EventTarget
import top.davidon.ironware.events.EventPlayerUpdate
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.utils.Utils

class AutoJump : Module("AutoJump", ModuleCategory.PLAYER) {
    var shifting = BooleanSetting("Cancel when shifting", 0, true)
    private var c = false

    init {
        settings.addSetting(shifting)
    }

    @EventTarget
    fun onEvent(e: EventPlayerUpdate) {
        if (enabled) {
            if (mc.thePlayer.onGround && (!shifting.get() || !mc.thePlayer.isSneaking())) {
                if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.entityBoundingBox.offset(mc.thePlayer.motionX / 3.0, -1.0, mc.thePlayer.motionZ / 3.0)).isEmpty()) {
                    Utils.ju(true)
                    this.c = true
                } else if (this.c) {
                    Utils.ju(false)
                    this.c = false
                }
            } else if (this.c) {
                Utils.ju(false)
                this.c = false
            }
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
        Utils.ju(false)
        this.c = false
    }
}