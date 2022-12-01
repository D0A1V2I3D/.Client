package top.davidon.ironware.modules.render

import com.darkmagician6.eventapi.EventTarget
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.tileentity.TileEntityEnderChest
import top.davidon.ironware.events.EventRender
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.ColorSetting
import top.davidon.ironware.utils.RenderUtils
import java.awt.Color

class ChestESP : Module("ChestESP", ModuleCategory.RENDER) {
    var chestColor = ColorSetting("Normal Chest color", 0, Color(230, 115, 0))
    var echestColor = ColorSetting("Enderchest Color", 1, Color(0, 0, 190))

    init {
        settings.addSetting(chestColor)
        settings.addSetting(echestColor)
    }

    @EventTarget
    fun onEvent(e: EventRender) {
        if (enabled && e.type == EventRender.Type.RENDER_3D) {
            for (tile in mc.theWorld.loadedTileEntityList) {
                if (tile is TileEntityChest) {
                    RenderUtils.startDrawing()
                    RenderUtils.drawSolidBlockESP(tile.getPos(), chestColor.get().getRGB())
                    RenderUtils.stopDrawing()
                } else if (tile is TileEntityEnderChest) {
                    RenderUtils.startDrawing()
                    RenderUtils.drawSolidBlockESP(tile.getPos(), echestColor.get().getRGB())
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