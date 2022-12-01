package top.davidon.ironware.modules.render

import com.darkmagician6.eventapi.EventTarget
import top.davidon.ironware.events.EventRender
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.utils.RenderUtils

class Tracers : Module("Tracers", ModuleCategory.RENDER) {
    var invis = BooleanSetting("Include invisible players", 0, true)

    init {
        settings.addSetting(invis)
    }

    @EventTarget
    fun onEvent(e: EventRender) {
        if (enabled && e.type == EventRender.Type.RENDER_3D) {
            for (player in this.mc.theWorld.playerEntities) {
                if (player.isEntityAlive && player != mc.thePlayer) {
                    if (!invis.get() && player.isInvisible) return
                    val posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * e.partialTicks - mc.renderManager.renderPosX
                    val posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * e.partialTicks - mc.renderManager.renderPosY
                    val posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.partialTicks - mc.renderManager.renderPosZ
                    var old = mc.gameSettings.viewBobbing

                    RenderUtils.startDrawing()
                    mc.gameSettings.viewBobbing = false
                    mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2)
                    mc.gameSettings.viewBobbing = old
                    RenderUtils.drawLine(player, arrayOf(1.0, 0.0, 0.0).toDoubleArray(), posX, posY + player.eyeHeight, posZ)
                    RenderUtils.stopDrawing()
                }
            }
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}