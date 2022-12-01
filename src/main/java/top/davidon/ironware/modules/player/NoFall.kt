package top.davidon.ironware.modules.player

import com.darkmagician6.eventapi.EventTarget
import net.minecraft.network.play.client.C03PacketPlayer
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory

class NoFall : Module("NoFall", ModuleCategory.PLAYER) {
    @EventTarget
    fun onEvent(e: EventTarget) {
        if (enabled) {
            if (mc.thePlayer.fallDistance > 2)
                mc.thePlayer.sendQueue.addToSendQueue(C03PacketPlayer(true))
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}