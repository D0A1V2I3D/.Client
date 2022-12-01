package top.davidon.ironware.modules.render

import com.darkmagician6.eventapi.EventTarget
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import top.davidon.ironware.Client
import top.davidon.ironware.events.EventDraw
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.settings.settings.ColorSetting
import top.davidon.ironware.settings.settings.NumberRangeSetting
import java.awt.Color

class TabGui : Module("ArrayList", ModuleCategory.RENDER) {
    var scale = NumberRangeSetting("Scale", 0, 1.0, 0.0, 10.0, 0.1)
    var xoffset = NumberRangeSetting("XOffset", 1, 5.0, 0.0, 150.0, 1.0)
    var yoffset = NumberRangeSetting("YOffset", 2, 5.0, 0.0, 150.0, 1.0)
    var gap = NumberRangeSetting("Gap", 3, 5.0, 0.0, 50.0, 1.0)
    var color = ColorSetting("Text color", 4, Color(255, 255, 0))
    var hideRender = BooleanSetting("Hide render modules", 5, false)

    init {
        settings.addSetting(scale)
        settings.addSetting(gap)
        settings.addSetting(xoffset)
        settings.addSetting(yoffset)
        settings.addSetting(color)
    }

    @EventTarget
    fun onEvent(e: EventDraw) {
        if (enabled) {
            val sr = ScaledResolution(mc)
            Client.moduleManager.modules.sortWith(Comparator.comparingInt { m: Any ->
                mc.fontRendererObj.getStringWidth(
                    (m as Module).name
                )
            }.reversed())
            var count = 0
            GlStateManager.pushMatrix()
            GlStateManager.translate(sr.scaledWidth - xoffset.get(), yoffset.get(), 0.0)
            GlStateManager.scale(scale.get(), scale.get(), 1.0)
            GlStateManager.translate(-(sr.scaledWidth - xoffset.get()), -yoffset.get(), 0.0)
            for (m in Client.moduleManager.modules) {
                if (m.enabled && !m.hidden && !hideRender.get() && m.category === ModuleCategory.RENDER) {
                    mc.fontRendererObj.drawStringWithShadow(
                        m.name,
                        (sr.scaledWidth - mc.fontRendererObj.getStringWidth(m.name) - xoffset.get()).toFloat(),
                        (yoffset.get() + count * (mc.fontRendererObj.FONT_HEIGHT + gap.get())).toFloat(),
                        color.get().rgb
                    )
                    count++
                }
            }
            GlStateManager.popMatrix()
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}