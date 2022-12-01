package top.davidon.ironware.modules.render

import com.darkmagician6.eventapi.EventTarget
import com.darkmagician6.eventapi.types.EventType
import net.minecraft.client.renderer.GlStateManager
import top.davidon.ironware.events.EventCrosshair
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.NumberRangeSetting

class Crosshair : Module("Crosshair", ModuleCategory.RENDER) {
    var rotation = NumberRangeSetting("Rotation", 0, 0.0, 0.0, 360.0, 1.0)

    init {
        settings.addSetting(rotation)
    }

    @EventTarget
    fun onEvent(e: EventCrosshair) {
        if (enabled) {
            if (e.type === EventType.PRE) {
                GlStateManager.pushMatrix()
                GlStateManager.translate((e.sr.scaledWidth / 2).toDouble(), (e.sr.scaledHeight / 2).toDouble(), 0.0)
                GlStateManager.rotate(rotation.get().toFloat(), 0f, 0f, 1f)
                GlStateManager.translate(
                    (-(e.sr.scaledWidth / 2)).toDouble(),
                    (-(e.sr.scaledHeight / 2)).toDouble(), 0.0
                )
            } else if (e.type === EventType.POST) {
                GlStateManager.popMatrix()
            }
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}