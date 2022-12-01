package top.davidon.ironware.modules.render

import com.darkmagician6.eventapi.EventTarget
import net.minecraft.entity.player.EntityPlayer
import top.davidon.ironware.events.EventRender
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.settings.settings.ColorSetting
import top.davidon.ironware.utils.RenderUtils
import java.awt.Color

class PlayerESP : Module("PlayerESP", ModuleCategory.RENDER) {
    var box = BooleanSetting("Box", 0, false)
    var boxColor = ColorSetting("Box color", 2, Color(255, 0, 0))
    var glow = BooleanSetting("Glow", 1, true)
    var invis = BooleanSetting("Include invis players", 3, true)

    init {
        settings.addSetting(box)
        settings.addSetting(boxColor)
        settings.addSetting(glow)
    }

    @EventTarget
    fun onEvent(e: EventRender) {
        if (enabled && e.type == EventRender.Type.RENDER_3D && box.get()) {
            for (entity in mc.theWorld.loadedEntityList) {
                if (entity is EntityPlayer && entity.name != mc.thePlayer.name && entity.isEntityAlive) {
                    if (!invis.get() && entity.isInvisible) return
                    RenderUtils.startDrawing()
                    RenderUtils.drawOutlinedEntityESP(entity.posX, entity.posY, entity.posZ, entity.width.toDouble(), entity.height.toDouble(), 1F, 0F, 0F, 1F)
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