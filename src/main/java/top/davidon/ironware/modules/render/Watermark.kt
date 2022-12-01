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

class Watermark : Module("Watermark", ModuleCategory.RENDER) {
    var colorSet = ColorSetting("Text Color", 3, Color(255, 255, 0))
    var scaleSet = NumberRangeSetting("Scale", 0, 1.3, 0.0, 10.0, 0.1)
    var xoffsetSet = NumberRangeSetting("XOffset", 1, 5.0, 0.0, 150.0, 1.0)
    var yoffsetSet = NumberRangeSetting("YOffset", 2, 5.0, 0.0, 150.0, 1.0)
    var vernumSet = BooleanSetting("Version number", 4, true)

    init {
        settings.addSetting(colorSet)
        settings.addSetting(scaleSet)
        settings.addSetting(xoffsetSet)
        settings.addSetting(yoffsetSet)
        settings.addSetting(vernumSet)
    }

    @EventTarget
    fun onEvent(e: EventDraw) {
        if (enabled) {
            val sr = ScaledResolution(mc)
            GlStateManager.pushMatrix()
            GlStateManager.translate(xoffsetSet.get(), yoffsetSet.get(), 0.0)
            GlStateManager.scale(scaleSet.get(), scaleSet.get(), 1.0)
            GlStateManager.translate(-xoffsetSet.get(), -yoffsetSet.get(), 0.0)
            if (vernumSet.get()) {
                mc.fontRendererObj.drawStringWithShadow(
                    Client.name + " v" + Client.version,
                    xoffsetSet.get().toFloat(), yoffsetSet.get().toFloat(), colorSet.get().rgb
                )
            } else {
                mc.fontRendererObj.drawStringWithShadow(
                    Client.name,
                    xoffsetSet.get().toFloat(), yoffsetSet.get().toFloat(), colorSet.get().rgb
                )
            }
            GlStateManager.popMatrix()
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}