package top.davidon.ironware.utils

import com.google.gson.JsonObject
import com.sun.management.HotSpotDiagnosticMXBean
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C01PacketChatMessage
import net.minecraft.network.play.server.S0FPacketSpawnMob
import net.minecraft.network.play.server.S27PacketExplosion
import net.minecraft.util.IChatComponent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import top.davidon.ironware.Client
import java.lang.management.ManagementFactory
import kotlin.system.exitProcess

class Utils {
    companion object {
        @JvmStatic
        var logger: Logger = LogManager.getLogger()
        var mc: Minecraft = Minecraft.getMinecraft()

        fun sendClientMessage(m: String?) {
            if (isMcNull()) {
                return
            }
            val message = java.lang.String.format("<§l§5%s§r> %s", Client.name, m)
            val json = JsonObject()
            json.addProperty("text", message)
            mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(json.toString()))
        }

        fun isMcNull(): Boolean {
            return mc.thePlayer == null && mc.theWorld == null
        }

        fun sendMessageToServer(m: String?) {
            if (isMcNull()) {
                return
            }
            mc.thePlayer.sendQueue.addToSendQueue(C01PacketChatMessage(m))
        }

        @JvmStatic
        fun isCrashPacket(packetIn: Packet<*>?): Boolean {
            if (packetIn is S0FPacketSpawnMob && mc.theWorld != null && mc.theWorld.loadedEntityList.size > 1500) {
                return true
            }
            if (packetIn is S27PacketExplosion) {
                val packet = packetIn
                val maxValue = 100000000.0
                if (packet.func_149144_d() >= maxValue || packet.func_149147_e() >= maxValue || packet.func_149149_c() >= maxValue || packet.x >= maxValue || packet.y >= maxValue || packet.z >= maxValue) {
                    return true
                }
            }
            return false
        }

        fun acceptsJavaAgentsAttachingToThisJvm() {
            val vm = ManagementFactory.getPlatformMXBean(
                HotSpotDiagnosticMXBean::class.java
            )
            val opt = vm.getVMOption("DisableAttachMechanism")
            val bool = "false" == opt.value || opt.isWriteable
            if (!bool) exitProcess(-11)
        }

        fun isInside(mouseX: Int, mouseY: Int, x: Double, y: Double, width: Double, height: Double): Boolean {
            return mouseX > x && mouseX < width && mouseY > y && mouseY < height
        }

        fun isInside(mouseX: Int, mouseY: Int, x: Int, y: Int, width: Int, height: Int): Boolean {
            return mouseX > x && mouseX < width && mouseY > y && mouseY < height
        }

        fun ju(ju: Boolean) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.keyCode, ju)
        }
    }
}